import java.util.Random;

public class DesignConfig {
    // מחלקה זו משמשת כסוג של קובץ הגדרות למשחק
    // נוכל להגדיר פה את עיצוב המשחק, הדפסת החוקים, ועוד.

    private static Random rnd = new Random();

    private static String marked_sym = "🚩";  // תא שהשחקן סימן בתור פצצה אפשרית
    private static String hidden_sym = "▫";  // תא שהשחקן עוד לא גילה
    private static String empty_sym = " ";  // השחקן יודע שמאחורי התא נמצא המספר 0
    private static String bomb_sym = "💣";  // פצצה במצב הסופי של המשחק
    private static String clear_sym = " ";  // תא נקי במצב הסופי של המשחק

    private static String marking_keyword = "MARK";  // המילה שהשחקן יצטרך להקליד כדי לסמן פצצה

    // לעיצוב בלבד
    private static String[] decorative = {"⭐", "💥", "💢", "🔸", "🔹", "💫", "🪐", "🌌", "🔥", "🌴"};
    private static String decorative_line = "━";
    private static String input_postfix = " 👉 ";
    private static String input_prefix = "💨 ";


    public String rules(int size) {
        // פעולה המחזירה את ההסבר שמופיע בתחילת המשחק

        String str;

        str = "WELCOME TO ALON'S MINESWEEPER GAME! 🔥\n";
        str += String.format("In this round, you will have %d mines. 💥\n", size);
        
        str += "Your goal is to find all of them, and mark them! 🏴\n";
        
        str += "You can explore new cells if you think they are not mines\n";
        str += "by just typing their location each round (row, column) 🚀\n";
        
        str += "Or, you can mark a location that you think is a mine by \n";
        str += String.format("typing \"%s\" in the beginning of each round, and only then\n", DesignConfig.marking_keyword);
        str += "typing the location in. 😊\n";

        str += "Keep in mind that even if you mark a mine, you can unmark it later. 🧨\n";

        str += String.format("The game will end when you mark %d mines, or if you explore a\n", size);
        str += "mine and explode... anyway, ENJOY! 🍂🎮\n";

        return str;
    }

    public String round_rules() {
        // פעולה התחזיר את ההסבר שמופיע לפני כל תור

        String str;
        
        str = "Select a place you want to explore! 🎯\n";
        str += "just type the row and then the column of the cell. 🎊\n";
        str += String.format("If you want to mark or unmark a mine, simply type \"%s\"! 💡", DesignConfig.marking_keyword);

        return str;

    }

    public String marking_rules() {
        // פעולה התחזיר את ההסבר שמופיע לפני סימון פצצה על ידי השחקן

        String str;

        str = "Please type the row (and then the column) of the cell\n";
        str += "you are willing to mark or unmark as a mine! 📌💣";

        return str;
    }

    public String winning() {
        // הטקסט שיודפס כאשר השחקן ינצח במשחק
        
        String str;

        str = "Well done, you did it! 🎊🎉✨\n";
        str += "You won the game. ⚡✅✌";

        return str;
    }

    public String losing_to_false_bombs() {
        // הטקסט שיודפס כאשר השחקן יפסיד אחרי שסימן פצצות לא נכונות

        String str;
        
        str = "Mistakes were made... 🙄\n";
        str += "It was close, but you didn't mark the right mine locations. 😧\n";
        str += "The game is over. 🙁";

        return str;
    }

    public String losing_by_exploring_bomb() {
        
        String str;

        str = "KA-BOOM! 💥💣🤯\n";
        str += "The place you wanted to explore wasn't the safest... 😓\n";
        str += "The game is over. 🤨";

        return str;
    }

    public String random_decorative() {
        // מחזיר אימוגי קישוט רנדומלי מהרשימה
        int rnd_index = rnd.nextInt(DesignConfig.decorative.length);
        return DesignConfig.decorative[rnd_index];
    }

    public String line(int size) {
        // יחזיר קו מפריד
        
        String decorative_emoji = this.random_decorative();

        String str = "\n" + decorative_emoji + " ";
        for (int i = 0; i < size; i++) {
            str += DesignConfig.decorative_line;
        }
        str += " " + decorative_emoji + "\n";

        return str;
    }

    public String input_request(String request) {
        // יחזיר את התו שמופיע בסוף השורה המבקשת קלט
        return DesignConfig.input_prefix + request + DesignConfig.input_postfix;
    }

    public String line() {
        // יחזיר קו מפריד
        
        return this.line(15);
    }

    public String marking_keyword() {
        return DesignConfig.marking_keyword;
    }

    public String marked() {
        return DesignConfig.marked_sym;
    }

    public String hidden() {
        return DesignConfig.hidden_sym;
    }

    public String empty() {
        return DesignConfig.empty_sym;
    }

    public String bomb() {
        return DesignConfig.bomb_sym;
    }

    public String clear() {
        return DesignConfig.clear_sym;
    }
}