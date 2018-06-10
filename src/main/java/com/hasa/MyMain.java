package com.hasa;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * DockerIntegrationTests
 *
 * @author hasantha.alahakoon
 */
public class MyMain
{
    public static void main(String[] args) throws MojoExecutionException
    {
        MSSQLDockerContainer.startDB("demodb","NovaIntDB", 3221225472L);
        MSSQLDockerContainer.stopAndRemoveDB();
        System.exit(0);
    }



//    public static void main(String[] args) throws DockerCertificateException, DockerException, InterruptedException, IOException
//    {
//        final DockerClient docker = DefaultDockerClient.fromEnv().build();
//        final HostConfig hostConfig = HostConfig.builder().memory(3221225472L).build(); // hardcoded 3gb for now
//        final ContainerCreation container = docker.createContainer(ContainerConfig.builder().image("demodb").hostConfig(hostConfig).build(), "NovaIntDB");
//        docker.startContainer(container.id());
//        System.out.println(container.id());
//
//
//        try (PipedInputStream stdout= new PipedInputStream(); PipedInputStream stderr = new PipedInputStream(); PipedOutputStream stdout_pipe = new PipedOutputStream(stdout); PipedOutputStream stderr_pipe=new PipedOutputStream(stderr))
//        {
//            ExecutorService executorService =
//                    new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
//                            new LinkedBlockingQueue<Runnable>());
//
//            executorService.submit(new Callable<Void>()
//            {
//                public Void call() throws Exception
//                {
//                    LogStream logStream = docker.attachContainer(container.id(), DockerClient.AttachParameter.LOGS, DockerClient.AttachParameter.STDOUT,
//                            DockerClient.AttachParameter.STDERR, DockerClient.AttachParameter.STREAM);
//                    logStream.attach(stdout_pipe, stderr_pipe);
//                    return null;
//                }
//            }
//            );
//
//            String line = null;
//            try (Scanner sc = new Scanner(stdout); Scanner sc_stderr = new Scanner(stderr)) {
//                while (!(line = sc.nextLine()).equals(""))
//                {
//                    System.out.println(line);
//                }
//            }
//            System.out.println("Time to run the integration testss");
//
//            docker.stopContainer(container.id(),10);
//            docker.removeContainer(container.id());
//            System.out.println("DONE");
//            docker.close();
//            System.exit(0);
//        }
//    }
}
