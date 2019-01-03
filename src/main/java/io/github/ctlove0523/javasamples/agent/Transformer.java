package io.github.ctlove0523.javasamples.agent;

import java.io.FileOutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Transformer
 *
 * @author c00382802
 * 2018/12/6
 */
public class Transformer implements ClassFileTransformer {
    private static final String FILE_PATH = "D:\\18.5\\bus";
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className != null) {
            // Skip all system classes
            if (!className.startsWith("java")
                && !className.startsWith("sun")
                && !className.startsWith("javax")
                && !className.startsWith("com")
                && !className.startsWith("com")
                && !className.startsWith("jdk")
                && !className.startsWith("org")
                && !className.startsWith("net")
                && !className.startsWith("ch")) {

                // Replace all separator charactors
                String newName = className.replaceAll("/", "#") + ".class";

                try {
                    FileOutputStream fos = new FileOutputStream(newName);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (Exception ex) {
                }
            }
        }
        // We are not modifying the bytecode in anyway, so return it as-is
        return classfileBuffer;
    }
}
