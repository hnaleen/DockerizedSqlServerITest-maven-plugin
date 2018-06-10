package com.hasa;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * DockerIntegrationTests
 *
 * @author hasantha.alahakoon
 */
@Mojo(name = "startDB")
public class StartDbMojo extends AbstractMojo
{
    @Parameter(required = true)
    String imageName;

    @Parameter(defaultValue = "NovaIntDB")
    String containerName;

    @Parameter(defaultValue = "3221225472")
    Long memory;

    public void execute() throws MojoExecutionException //TODO Port bindings
    {
        getLog().info("++++++++++++++ Starting SQL Server ++++++++++++++++++++++");
        MSSQLDockerContainer.startDB(imageName, containerName, memory);
        getLog().info("++++++++++++++ SQL Server is up ++++++++++++++++++++++");
    }
}
