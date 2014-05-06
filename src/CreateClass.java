import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.*;

import static org.objectweb.asm.Opcodes.*;

public class CreateClass {
    public static void main(String[] args) throws IOException {
        byte[] bytecode = generateByteCode();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("NewClass.class"));
        bufferedOutputStream.write(bytecode);
        bufferedOutputStream.close();
    }

    private static byte[] generateByteCode() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "NewClass", null, "java/lang/Object", null);

        FieldVisitor fv = cw.visitField(ACC_PRIVATE, "field", "I", null, null);
        fv.visitEnd();

        MethodVisitor constructorVisitor = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        constructorVisitor.visitCode();
        constructorVisitor.visitVarInsn(ALOAD, 0);
        constructorVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        constructorVisitor.visitInsn(RETURN);

        // ASM ignores the values when COMPUTE_MAXS is
        // used - however You still need to invoke this method
        constructorVisitor.visitMaxs(0, 0);
        constructorVisitor.visitEnd();

        MethodVisitor getterVisitor = cw.visitMethod(ACC_PUBLIC, "getField", "()I", null, null);
        getterVisitor.visitCode();
        getterVisitor.visitVarInsn(ALOAD, 0);
        getterVisitor.visitFieldInsn(GETFIELD, "NewClass", "field", "I");
        getterVisitor.visitInsn(IRETURN);
        getterVisitor.visitMaxs(0, 0);
        getterVisitor.visitEnd();

        MethodVisitor setterVisitor = cw.visitMethod(ACC_PUBLIC, "setField", "(I)V", null, null);
        setterVisitor.visitCode();
        setterVisitor.visitVarInsn(ALOAD, 0);
        setterVisitor.visitVarInsn(ILOAD, 1);
        setterVisitor.visitFieldInsn(PUTFIELD, "NewClass", "field", "I");
        setterVisitor.visitInsn(RETURN);
        setterVisitor.visitMaxs(0, 0);
        setterVisitor.visitEnd();

        cw.visitEnd();

        return cw.toByteArray();
    }
}
