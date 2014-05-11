public final class FinalClass {

    private void hardToCallMe() {
        System.out.println("inside hardToCallMe");
    }

    public void callMe() {
        System.out.println("inside callMe");
        hardToCallMe(); 
    }
    
}