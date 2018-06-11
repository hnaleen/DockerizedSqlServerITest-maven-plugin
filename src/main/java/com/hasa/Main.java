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
public class Main
{
    public static void main(String[] args) throws MojoExecutionException
    {
        MSSQLDockerContainer.startDB("demodb","NovaIntDB", "15788", 3221225472L);
        MSSQLDockerContainer.stopAndRemoveDB();
        System.exit(0);
    }
}
