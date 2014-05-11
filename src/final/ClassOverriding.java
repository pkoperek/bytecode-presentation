import org.objectweb.asm.*;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassOverriding {

    public void redefineClass() throws Exception {
        InputStream finalClassAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("FinalClass.class");

        ClassReader classReader = new ClassReader(finalClassAsStream);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classReader.accept(new RedefiningClassVisitor(classWriter), 0);

        redefineClass("FinalClass", classWriter.toByteArray());
    }

    private void redefineClass(String classToRedefine, byte[] newBytecode) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object[] args = new Object[]{classToRedefine, newBytecode, 0, newBytecode.length};
        Class classLoaderClass = Class.forName("java.lang.ClassLoader");
        Method defineClass = classLoaderClass.getDeclaredMethod("defineClass",
                new Class[]{String.class,
                        byte[].class,
                        Integer.TYPE,
                        Integer.TYPE});
        defineClass.setAccessible(true);
        defineClass.invoke(ClassLoader.getSystemClassLoader(), args);
    }

    private static class RedefiningClassVisitor extends ClassVisitor {

        public RedefiningClassVisitor(ClassWriter classWriter) {
            super(Opcodes.ASM5, classWriter);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if ("hardToCallMe".equals(name)) {
                MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
                methodVisitor.visitCode();
                methodVisitor.visitInsn(Opcodes.RETURN);
                methodVisitor.visitEnd();

                return null;
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }
}
