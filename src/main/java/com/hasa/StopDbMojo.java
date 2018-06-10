package com.hasa;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * DockerIntegrationTests
 *
 * @author hasantha.alahakoon
 */
@Mojo(name = "stopDB")
public class StopDbMojo extends AbstractMojo
{
    @Override
    public void execute() throws MojoExecutionException
    {
        getLog().info("++++++++++++++ Stopping SQL Server ++++++++++++++++++++++");
        MSSQLDockerContainer.stopAndRemoveDB();
        getLog().info("++++++++++++++ Stopped SQL Server ++++++++++++++++++++++");
    }
}
