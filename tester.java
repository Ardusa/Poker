public class tester {
    public static void main(String[] args) {
        String word = "Wingardium Leviosa";
        for (int i = 0; i < word.length(); i += 2) {
            System.out.print(word.substring(i, i+1));
        }
    }
}
