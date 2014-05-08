class ClassToBeProxied {

    public void toCallOne() {
        System.out.println("toCallOne");
    }

    public void toCallTwo() {
        System.out.println("toCallTwo");
    }

    public static void main(String[] args) {
        ClassToBeProxied classToBeProxied = new ClassToBeProxied();
        classToBeProxied.toCallOne();
        classToBeProxied.toCallTwo();
    }
}
