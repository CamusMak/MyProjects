import java.util.Arrays;

import java.util.Scanner;

import static java.lang.Math.random;
import static java.lang.Thread.sleep;

public class Hangman {

    public static void main(String[] args) throws InterruptedException {


        String[] words = new String[]{"Algorithm", "Argument", "Bookkeeping", "Arrays", "Variable", "Java", "Capital", "Liability", "Computer", "Database"};


        //  short definitions of secret words
        String[] descriptions = new String[]{"A set of instructions that are followed to solve a problem. It's a computer's thought process.",
                "A way to provide more information to a function. The function can then use that information as it runs, like a variable.",
                "A method of accounting that involves the timely recording of all financial transactions for the business.",
                "Containers that hold variables; they're used to group together similar variables.",
                "A container that holds a single number, word, or other information that you can use throughout a program.",
                "This is a powerful multi-platform programming language. ",
                "Refers to the overall wealth of a business as demonstrated by its cash accounts, assets, and investments.",
                "This business finance key term is a legal obligation to repay or otherwise settle a debt. ",
                "An electronic machine that is used for storing, organizing, " +
                        "\nand finding words, numbers, and pictures, for doing calculations, and for controlling other machines",
                "A large amount of information stored in a computer system in such a way that it can be easily looked at or changed:"

        };

        Play(words, descriptions);


    }


    // getWord returns word from the word list
    public static String getWord(String[] list) {
        for (int i = list.length - 1; i > 0; i--) {
            int rand = (int) (random() * (i + 1));
            String word = list[i];
            list[i] = list[rand];
            list[rand] = word;

        }
        return list[0];
    }

    // toWord method converts String[] array into word for representation
    public static String toWord(String[] list) {
        String word = "";
        for (String letter : list) {
            word += letter;
        }
        return word;
    }


    // toArray method converts a word into arrays, which can be used for iteration over word characters
    public static String[] toArray(String word) {
        String[] array = new String[word.length()];

        for (int i = 0; i < word.length(); i++) {
            array[i] = String.valueOf(word.charAt(i));
        }
        return array;
    }


    // findIndex returns the index of given word
    public static int findIndex(String[] words, String word) {
        int index = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word)) {
                index = i;
            }
        }
        return index;
    }


    // here is description of the game and the main function which will run the game
    public static void Play(String[] words, String[] descriptions) throws InterruptedException {
        main:
        while (true) {

            Scanner input = new Scanner(System.in);
            System.out.println("Author: Alber Makaryan");
            System.out.println("\n\nThis is a simple guessing game, where you need to guess the random selected word from words array." +
                    "\nYou will be provided short definition of the word, which makes your job easier. \nYou can input either a single letter, or" +
                    "\na part of the word (some letters) but with the same sequence as in the word, " +
                    "\notherwise only the first letter will be checked if the word contains it or not. " +
                    "\nIf secret word contains more than one of inputted char, only the first one will be unveiled." +
                    "\nYou have six chance to fail.\nGood luck! ");

            sleep(500);
            System.out.println("\n\nIf you want to skip and change the word enter 'skip'. ");
            System.out.println("If you want to quit during game enter 'quit'.\nIf you need hint, enter 'hint' to read description one more!\n");
            sleep(300);

            System.out.print("Enter 'start' to start the game: ");
            String start = input.next();
            if (start.toLowerCase().equals("start")) {
                System.out.println("\n\n");
                sleep(50);
                Game(words, descriptions);
                break main;  // this breaks program user wants to quit
            } else {
                break main;
            }

        }
    }


    public static void Game(String[] words, String[] descriptions) throws InterruptedException{
        int word_guessed = 0;
        main:
        while (true) {
            String[] gallows = {Man.pos_1(), Man.pos_2(), Man.pos_3(), Man.pos_4(), Man.pos_5(), Man.pos_6()};
            Scanner input = new Scanner(System.in);
            String[] words_copy = words.clone(); // since getWord changes value-index initial positions of words array,
            // we need copy of words array where initial position are kept, which will be
            // to get index of the secret word
            String word = getWord(words_copy);
            int index = findIndex(words, word); // this index will be used to print description of the secret word
            word = word.toUpperCase();

            String[] word_array = toArray(word);  // word_array is the array version of secret word,
            String[] blank_array = new String[word.length()];
            Arrays.fill(blank_array, "*");

            // this will print short definition for the secret word
            String desc = descriptions[index];
            System.out.println("DEFINITION OF THE WORD:\n" + desc + "\n" + "\nTry to guess the word! ");


            String prompt = "\nYour input: ";
            int count = 0;
            int count_wrong = 0;// this is to count how many tries it takes the user to guess the secret word
            nested:
            while (true) {
                sleep(20);

                count += 1;

                // blank_array is an array filled with "*"․Every time user guesses the correct letter, the asterisk
                // of the same index as that letter has in secret word converts into that letter, and the toWord()
                // method converts blank_array into "normal" String word
                String guessedWord = toWord(blank_array);
                sleep(50);
                System.out.println("Word: " + guessedWord);
                System.out.print(prompt);
                String guess = input.next().toUpperCase();
                String check_word = toWord(word_array);  // since guessed characters from secret word converts to "*", we
                // need copy of secret word to check with guessedWord

                // if user wants to skip current word
                if (guess.toLowerCase().equals("skip")) {
                    System.out.println("_".repeat(100) + "\n");
                    Game(words, descriptions);


                }
                // if user want to quit the game
                else if (guess.toLowerCase().equals("quit")) {
                    System.out.println("Guessed words: " + word_guessed);
                    break main;

                } else if (guess.toLowerCase().equals("hint")) {
                    System.out.println("\nDESCRIPTION:\n" + desc + "\n");
                    count -= 1;
                }
                // case when user inputs whole words instantly
                else if (guess.equals(word)) {
                    word_guessed += 1;
                    System.out.println("\nSupper! You guessed instantly!! \n Guessed words: " + word_guessed);
                    break nested;
                }
                // if user inputs more than one character, and the word contain that character's with the same sequence
                else {
                    if (check_word.contains(guess)) {
                        for (int i = 0; i < guess.length(); i++) {
                            int ind = check_word.indexOf(guess.charAt(i));
                            blank_array[ind] = String.valueOf(check_word.charAt(ind));
                            word_array[ind] = "*"; // convert all element to "*" if it already guessed
                            check_word = toWord(word_array);
                            guessedWord = toWord(blank_array);
                            prompt = "\nVery good!!! Keep going! ";
                        }
//                        if (count_wrong > 1) {
//                            count_wrong -= 1;
//                            System.out.println("\n" + gallows[count_wrong -1] + "\n");
//                        }
                    }
                    // if user input random characters or only one character
                    else if (check_word.contains(String.valueOf(guess.charAt(0)))) {
                        int ind = check_word.indexOf(guess.charAt(0));
                        blank_array[ind] = String.valueOf(check_word.charAt(ind));
                        word_array[ind] = "*"; // remove element if it already guessed
                        check_word = toWord(word_array);
                        guessedWord = toWord(blank_array);
//                        if (count_wrong > 1) {
//                            count_wrong -= 1;
//                            System.out.println("\n" + gallows[count_wrong -1] + "\n");
//                        }
                        prompt = "\nWell done!!! Keep going! ";

                    }
                    // if input does not match
                    else {
                        if (count_wrong <= 5) {
                            if (count_wrong == 5) {

                                System.out.println(Man.pos_6());
                                String over = "GAME OVER!";
                                System.out.println("*".repeat(100));

                                for (int i = 0; i < over.length(); i++){
                                    sleep(100);
                                    System.out.print(over.charAt(i));
                                }
                                System.out.println(" ");
                                String over_ = "   "+word;
                                System.out.println("The secret word was: ");// + word.toUpperCase();
                                for (int i = 0; i < over_.length(); i++){
                                    sleep(100);
                                    System.out.print(over_.charAt(i));
                                }
                                System.out.println(" ");
                                break nested;
                            }
                            else {
                                System.out.print("\nWrong input! Be careful! \n");

                                System.out.println("\n" + gallows[count_wrong] + "\n");
                                prompt = "\nTry again!! ";
                            }
                            count_wrong += 1;
                        }
                    }
                }
//


                // if user guesses the word
                if (guessedWord.equals(word)) {
                    System.out.println("\n" + guessedWord);
                    word_guessed += 1;
                    System.out.println("\nWell done! It took you " + count + " tries to guess the secret word!\nWords guessed: " + word_guessed);
                    break nested;
                }
            }

            // ask if user wants to play again or quit. If yes, program repeats, else: break
            System.out.print("\nDo you want to play again (y/n)? ");
            String play_again = input.next().toUpperCase();
            if (play_again.toUpperCase().equals("Y")) {
                System.out.println("_".repeat(120) + "\n".repeat(3));
                continue;
            } else {
                System.out.println("Words guessed: " + word_guessed);
                break;
            }
        }
    }
}


class Man {
    public static String pos_1() {
        String gallows = (" ".repeat(11) + "_".repeat(7)) + "\n" +
                (" ".repeat(10) + "|/" + " ".repeat(6) + "|\n") +
                ((" ".repeat(10) + "|" + " ".repeat(7) + "|\n")) +
                (" ".repeat(10) + "|" + " ".repeat(6) +"(_)\n") +
                ((" ".repeat(10) + "|\n").repeat(5)) +
                (" ".repeat(9) + "/|\\") + "\n" +
                (("_".repeat(8) + "/_|_\\" + "_".repeat(9)));
        return gallows;
    }

    public static void print_pos_1() {
        System.out.println(pos_1());
    }

    public static String pos_2() {
        String gallows = (" ".repeat(11) + "_".repeat(7)) + "\n" +
                (" ".repeat(10) + "|/" + " ".repeat(6) + "|\n") +
                ((" ".repeat(10) + "|" + " ".repeat(7) + "|\n")) +
                (" ".repeat(10) + "|" + " ".repeat(6) +"(_)\n") +
                (" ".repeat(10)) + "|" + " ".repeat(7) + "|\n" +
                ((" ".repeat(10) + "|\n").repeat(4)) +
                (" ".repeat(9) + "/|\\") + "\n" +
                (("_".repeat(8) + "/_|_\\" + "_".repeat(9)));
        return gallows;

    }

    public static void print_pos_2() {
        System.out.println(pos_2());
    }

    public static String pos_3() {
        String gallows = (" ".repeat(11) + "_".repeat(7)) + "\n" +
                (" ".repeat(10) + "|/" + " ".repeat(6) + "|\n") +
                ((" ".repeat(10) + "|" + " ".repeat(7) + "|\n")) +
                (" ".repeat(10) + "|" + " ".repeat(6) +"(_)\n") +
                (" ".repeat(10)) + "|" + " ".repeat(5) + "/ | \n" +
                ((" ".repeat(10) + "|\n").repeat(4)) +
                (" ".repeat(9) + "/|\\") + "\n" +
                (("_".repeat(8) + "/_|_\\" + "_".repeat(9)));
        return gallows;
    }

    public static void print_pos_3() {
        System.out.println(pos_3());
    }

    public static String pos_4() {
        String gallows = (" ".repeat(11) + "_".repeat(7)) + "\n" +
                (" ".repeat(10) + "|/" + " ".repeat(6) + "|\n") +
                ((" ".repeat(10) + "|" + " ".repeat(7) + "|\n")) +
                (" ".repeat(10) + "|" + " ".repeat(6) +"(_)\n") +
                (" ".repeat(10)) + "|" + " ".repeat(5) + "/ | \\ \n" +
                ((" ".repeat(10) + "|\n").repeat(4)) +
                (" ".repeat(9) + "/|\\") + "\n" +
                (("_".repeat(8) + "/_|_\\" + "_".repeat(9)));
        return gallows;
    }

    public static void print_pos_4() {
        System.out.println(pos_4());
    }

    public static String pos_5() {
        String gallows = (" ".repeat(11) + "_".repeat(7)) + "\n" +
                (" ".repeat(10) + "|/" + " ".repeat(6) + "|\n") +
                ((" ".repeat(10) + "|" + " ".repeat(7) + "|\n")) +
                (" ".repeat(10) + "|" + " ".repeat(6) +"(_)\n") +
                (" ".repeat(10)) + "|" + " ".repeat(5) + "/ | \\ \n" +
                (" ".repeat(10) + "|" + " ".repeat(6) + "/\n") +
                ((" ".repeat(10) + "|\n").repeat(3)) +
                (" ".repeat(9) + "/|\\") + "\n" +
                (("_".repeat(8) + "/_|_\\" + "_".repeat(9)));
        return gallows;
    }

    public static void print_pos_5() {
        System.out.println(pos_5());
    }

    public static String pos_6() {
        String gallows = (" ".repeat(11) + "_".repeat(7)) + "\n" +
                (" ".repeat(10) + "|/" + " ".repeat(6) + "|\n") +
                ((" ".repeat(10) + "|" + " ".repeat(7) + "|\n")) +
                (" ".repeat(10) + "|" + " ".repeat(6) +"(_)\n") +
                (" ".repeat(10)) + "|" + " ".repeat(5) + "/ | \\ \n" +
                (" ".repeat(10) + "|" + " ".repeat(6) + "/ \\ \n") +
                ((" ".repeat(10) + "|\n").repeat(3)) +
                (" ".repeat(9) + "/|\\") + "\n" +
                (("_".repeat(8) + "/_|_\\" + "_".repeat(9)));
        return gallows;
    }

    public static void print_pos_6() {
        System.out.println(pos_6());
    }
}









