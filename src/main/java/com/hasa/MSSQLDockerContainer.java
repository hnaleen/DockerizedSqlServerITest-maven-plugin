package com.hasa;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * DockerIntegrationTests
 *
 * @author hasantha.alahakoon
 */
public class MSSQLDockerContainer
{
    private static DockerClient docker;
    private static ContainerCreation container;
    private static boolean dbStartUpSuccessful = false;

    public static void startDB(String imageName, String containerName, Long memory) throws MojoExecutionException
    {
        try
        {
            docker = DefaultDockerClient.fromEnv().build();
            final HostConfig hostConfig = HostConfig.builder().memory(memory).build();
            container = docker.createContainer(ContainerConfig.builder().image(imageName).hostConfig(hostConfig).build(), containerName);
            docker.startContainer(container.id());
            System.out.println(container.id());


            try (PipedInputStream stdout = new PipedInputStream(); PipedInputStream stderr = new PipedInputStream(); PipedOutputStream stdout_pipe = new PipedOutputStream(stdout); PipedOutputStream stderr_pipe = new PipedOutputStream(stderr)) {
                ExecutorService executorService =
                        new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>());

                DockerClient finalDocker = docker;
                executorService.submit(new Callable<Void>()
                                       {
                                           public Void call() throws Exception
                                           {
                                               LogStream logStream = finalDocker.attachContainer(container.id(), DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
                                                       DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM);
                                               logStream.attach(stdout_pipe, stderr_pipe);
                                               return null;
                                           }
                                       }
                );

                String line = null;
                try (Scanner sc = new Scanner(stdout); Scanner sc_stderr = new Scanner(stderr)) {
                    while (!(line = sc.nextLine()).equals("")) {
                        System.out.println(line);
                    }
                }
                dbStartUpSuccessful = true;
            }
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Error starting integration test DB", e);
        }
        finally
        {
            if (!dbStartUpSuccessful && docker != null)
            {
                docker.close();
                dbStartUpSuccessful = false;
            }
        }
    }

    public static void stopAndRemoveDB() throws MojoExecutionException
    {
        if (dbStartUpSuccessful)
        {
            try
            {
                docker.stopContainer(container.id(),10);
                docker.removeContainer(container.id());
            }
            catch (Exception e)
            {
                throw new MojoExecutionException("Error stopping or removing integration test DB", e);
            }
            finally
            {
                docker.close();
            }
        }
    }

}
