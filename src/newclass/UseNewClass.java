public class UseNewClass {
    public static void main(String[] args) {
        NewClass nc = new NewClass();
        nc.setField(111);
        System.out.println("Using: " + nc.getField() + " " + nc.getClass().getName());
    }    
}
