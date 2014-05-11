public class AppUsingFinalClassV2 {

    public static void main(String[] args) throws Exception {
        new ClassOverriding().redefineClass();

        new FinalClass().callMe();
    }

}
