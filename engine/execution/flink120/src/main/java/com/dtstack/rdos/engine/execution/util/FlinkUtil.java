package com.dtstack.rdos.engine.execution.util;

import org.apache.flink.client.program.PackagedProgram;
import org.apache.flink.client.program.ProgramInvocationException;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;

/**
 * Reason:
 * Date: 2017/2/21
 * Company: www.dtstack.com
 *
 * @ahthor xuchao
 */

public class FlinkUtil {

    private static String tmp_file_path = "/tmp";

    private static final String URL_SPLITE = "/";

    private static char fileSP = File.separatorChar;

    protected static PackagedProgram buildProgram(String jarFilePath, List<URL> classpaths,
                                                  String entryPointClass, String[] programArgs, SavepointRestoreSettings spSetting)
            throws FileNotFoundException, ProgramInvocationException {
        if (jarFilePath == null) {
            throw new IllegalArgumentException("The program JAR file was not specified.");
        }

        //FIXME 如何从远处读取文件
        File jarFile = new File(jarFilePath);

        // Check if JAR file exists
        if (!jarFile.exists()) {
            throw new FileNotFoundException("JAR file does not exist: " + jarFile);
        } else if (!jarFile.isFile()) {
            throw new FileNotFoundException("JAR file is not a file: " + jarFile);
        }

        // Get assembler class
        PackagedProgram program = entryPointClass == null ?
                new PackagedProgram(jarFile, classpaths, programArgs) :
                new PackagedProgram(jarFile, classpaths, entryPointClass, programArgs);

        program.setSavepointRestoreSettings(spSetting);

        return program;
    }

    public static String getTmpFileName(String fileUrl){
        String name = fileUrl.substring(fileUrl.lastIndexOf(URL_SPLITE));
        String tmpFileName = tmp_file_path  + fileSP + name;
        return tmpFileName;
    }

    public static void main(String[] args) {
        String url = "https://zhidao.baidu.com/question/426176937576742932.html";
        String fileName = getTmpFileName(url);
        System.out.println(fileName);
    }
}