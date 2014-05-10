class ClassToBeIntercepted {

    public void toCallOne() {
        System.out.println("toCallOne");
    }

    public void toCallTwo() {
        System.out.println("toCallTwo");
    }

    public static void main(String[] args) {
        ClassToBeIntercepted classToBeIntercepted = new ClassToBeIntercepted();
        classToBeIntercepted.toCallOne();
        classToBeIntercepted.toCallTwo();
    }
}
