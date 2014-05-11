import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * Creates the NewClass using ASM.
 * 
 * COMPUTE_MAXS - compute operand stack size.
 * The ACC_SUPER flag indicates which of two alternative semantics is to be expressed by the invokespecial instruction (§invokespecial) if it appears in this class. Compilers to the instruction set of the Java Virtual Machine should set the ACC_SUPER flag.
 * ACC_SUPER - http://stackoverflow.com/questions/8949933/what-is-the-purpose-of-the-acc-super-access-flag-on-java-class-files
 */

        /**
         * FieldVisitor visitField(int access,
         *                         String name,
         *                         String desc,
         *                         String signature,
         *                         Object value)
         *
         * Visits a field of the class.
         * Parameters:
         *      access - the field's access flags (see Opcodes). This parameter also indicates if the field is synthetic and/or deprecated.
         *      name - the field's name.
         *      desc - the field's descriptor (see Type).
         *      signature - the field's signature. May be null if the field's type does not use generic types.
         *      value - the field's initial value. This parameter, which may be null if the field does not 
         *              have an initial value, must be an Integer, a Float, a Long, a Double or a String (for 
         *              int, float, long or String fields respectively). This parameter is only used for static 
         *              fields. Its value is ignored for non static fields, which must be initialized through 
         *              bytecode instructions in constructors or methods.
         */
 
        /**
         * MethodVisitor visitMethod(int access,
         *                           String name,
         *                           String desc,
         *                           String signature,
         *                           String[] exceptions)
         * 
         * Visits a method of the class. This method must return a new MethodVisitor instance (or null) each time it is called, i.e., it should not return a previously returned visitor.
         * 
         * Parameters:
         *      access - the method's access flags (see Opcodes). This parameter also indicates if the method is synthetic and/or deprecated.
         *      name - the method's name.
         *      desc - the method's descriptor (see Type).
         *      signature - the method's signature. May be null if the method parameters, return type and exceptions do not use generic types.
         *      exceptions - the internal names of the method's exception classes (see getInternalName). May be null.
         */

        /**
         * public void visitMethodInsn(int opcode,
         *                             String owner,
         *                             String name,
         *                             String desc,
         *                             boolean itf)
         *
         * Visits a method instruction. A method instruction is an instruction that invokes a method.
         * 
         * Parameters:
         *      opcode - the opcode of the type instruction to be visited. This opcode is either INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
         *      owner - the internal name of the method's owner class (see getInternalName).
         *      name - the method's name.
         *      desc - the method's descriptor (see Type).
         *      itf - if the method's owner class is an interface. 
         */

        // ASM ignores the values when COMPUTE_MAXS is
        // used - however You still need to invoke this method
 
public class CreateClass {

    private static byte[] generateByteCode() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "NewClass", null, "java/lang/Object", null);

        FieldVisitor fv = cw.visitField(ACC_PRIVATE, "field", "I", null, null);
        fv.visitEnd();
        MethodVisitor constructorVisitor = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        constructorVisitor.visitCode();
        constructorVisitor.visitVarInsn(ALOAD, 0);
        constructorVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructorVisitor.visitInsn(RETURN);
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

    public static void main(String[] args) throws IOException {
        byte[] bytecode = generateByteCode();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("NewClass.class"));
        bufferedOutputStream.write(bytecode);
        bufferedOutputStream.close();
    }

}
