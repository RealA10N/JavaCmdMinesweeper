import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    
    private static Random rnd = new Random();
    private static Scanner input = new Scanner(System.in);

    private int size;  // גודל הלוח ומספר הפצצות במשחק
    private MineCell[][] cells;  // שומר את לוח המשחק, כאשר כל תא הוא אובייקט MineCell.
    private static DesignConfig design = new DesignConfig();  // מקרא המשחק. אובייקט משל עצמו המכיל את כל הסימונים במשחק

    private int marks = 0;  // מספר הסימונים של השחקן כפצצה על הלוח
    private int round = 0;  // מספר הסיבוב או התור של השחקן


    public Minesweeper(int size) {
        // הפרמטר של הגודל לא קובע רק את גודל הלוח
        // אלא גם את מספר הפצצות על הלוח.
        // הגודל המומלץ הוא 9 או 10!

        this.size = size;
        this.cells = new MineCell[this.size][this.size];
        this.marks = 0;

        // התחול הלוח
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.cells[row][col] = new MineCell(row, col);
            }
        }

        // הטלת הפצצות על הלוח
        for (int i = 0; i < this.size; i++) {
            this.plant_bomb();
        }

        this.generate_nearby_map();

    }

    private void plant_bomb() {
        // הטלת פצצה אחת על הלוח
        
        int row, col;
        row = rnd.nextInt(this.size);
        col = rnd.nextInt(this.size);

        if (this.cells[row][col].isBomb()) {
            // אם המקום שהוגרל כבר מכיל פצצה
            // יריץ את הפונקציה עוד הפעם, ויגריל פצצה אחרת - רקורסיה
            this.plant_bomb();
        } else {
            this.cells[row][col].setBomb();
        }
    }

    private void generate_nearby_map() {
        // פעולה הסופרת ומעדכנת את כל מספרי הפצצות ליד כל תא בלוח
        
        int near_count;

        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {

                near_count = this.count_nearby_bombs(row, col);
                this.cells[row][col].updateNearBombCount(near_count);
            }
        }
        
    }

    private MineCell[] get_nearby_cells(int row, int col) {
        // יחזיר את כל התאים מסביב לתא הנתון במערך

        // הגבלת המקסימום לגודל המערך - כדי לא לחרוג מגודל מערך
        int max_row = Math.min(this.size-1, row+1);
        int max_col = Math.min(this.size-1, col+1);

        // הגבלת המינימום לאפס - כדי לא לחרוג מגודל מערך
        int min_row = Math.max(0, row-1);
        int min_col = Math.max(0, col-1);

        int arr_size = ((max_row - min_row + 1) * (max_col - min_col + 1)) - 1;

        MineCell[] arr = new MineCell[arr_size];
        int arr_i = 0;
 
        for (int cur_row = min_row; cur_row <= max_row; cur_row++) {
            for (int cur_col = min_col; cur_col <= max_col; cur_col++) {
                if (cur_row != row || cur_col != col) {  // האינדקס של התא הרביעי הוא התא הנתון
                    arr[arr_i] = this.cells[cur_row][cur_col];
                    arr_i++;
                }
            }
        }

        return arr;

    }

    private int count_nearby_bombs(int row, int col) {
        // הפעולה מחזירה את מספר הפצצות המופיעות מסביב לנקודה הנתונה בלוח
        
        int count = 0;
        MineCell[] nearby_bombs = get_nearby_cells(row, col);

        for (int i = 0; i < nearby_bombs.length; i++) {
            if (nearby_bombs[i].isBomb()) {
                count++;
            }
        }

        return count;
    }

    public String toString() {
        // פעולה המחזירה את המצב הנוכחי של המשחק
        return this.toString(true);
    }

    public String toString(boolean flag_info) {
        // פעולה המחזירה את המצב הנוכחי של המשחק
        // אם הפרמטר אמת, ליד הלוח יודפס גם מידע על מספר
        // התור, ומספר הפצצות שמסומנות על הלוח


        // הדפסת כותרת
        String str = String.format("%4.4s", "");

        for (int i = 0; i < this.size; i++) {
            str += String.format("%2.2s ", i+1);
        }

        // הדפסת פתיח
        str += "\n   ╔══";

        for (int i = 0; i < this.size - 1; i++) {
            str += "═══";
        }

        str += "═╗\n";

        // הדפסת השורות בטבלה
        for (int i = 0; i < this.size; i++) {
            str += generateRowString(i, flag_info);
            str += "\n";
        }

        // הדפסת סוגר
        str += "   ╚══";

        for (int i = 0; i < this.size - 1; i++) {
            str += "═══";
        }

        str += "═╝\n";

        return str;
    }

    private String generateRowString(int row) {
        // פעולה המחזירה סטרינג רגיל של שורה בלוח
        // משומש בפונקציה טו-סטרינג

        String str;

        str = String.format("%2.2s ║", row+1);

        for (int col = 0; col < this.size; col++) {
            str += String.format("%2.2s ", this.cells[row][col].toString());
        }

        str += "║";

        return str;
    }

    private String generateRowString(int row, boolean flag_counter) {
        // פעולה המחזירה סטריג של שורה אחת בלוח,
        // ואם הפרמטר השני אמת והשורה היא השורה האמצעית,
        // יוסיף גם מידע על מספר הפצצות.

        String str = this.generateRowString(row);

        // הדפסת מספר המקומות המסומנים
        if (flag_counter && this.size / 2 == row) {
            str += String.format("   %2.2s %d / %d %2.2s", Minesweeper.design.marked(), this.marks, this.size, Minesweeper.design.bomb());
        }

        // הדפסת מספר הסיבוב
        if (flag_counter && this.size / 2 == row + 1) {
            str += String.format("   %2.2s ROUND #%d", Minesweeper.design.random_decorative(), this.round);
        }

        return str;
    }

    private boolean checkValidInputNumber(String string) {
        // בודק אם השרשור הנקלט הוא מספר בטווח התקין
        
        try {
            int num = Integer.parseInt(string);
            if (num < 1 || num > this.size) {
                return false;
            }
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private String scanUntilSuccess(String request, boolean mark) {
        // יבקש קלט עד שיתקבל קלט חוקי,  ויחזיר אותו
        // אם הפרמטר השני חוקי, גם המילה לסימון הפצצות תסומן
        
        String userin;

        request = Minesweeper.design.input_request(request);
        
        while (true) {
            System.out.print(request);
            userin = Minesweeper.input.next();

            if(mark && userin.equals(Minesweeper.design.marking_keyword())) {
                return userin;
            }

            if (this.checkValidInputNumber(userin)) {
                return userin;
            }
        }
    }

    private String markBomb() {
        // סימון פצצה
        // בעל 3 אפשרויות החזרה:
        // "win" - המשחק הסתיים והמשתמש ניצח
        // "lose" - המשחק הסתיים אך המשתמש הפסיד
        // "continue" - המשחק ממשיך
        
        int row, col, marker_value;
        boolean marked = false;

        do {
            System.out.println(Minesweeper.design.marking_rules() + "\n");
            row = Integer.parseInt(this.scanUntilSuccess("Marker row number", false));
            col = Integer.parseInt(this.scanUntilSuccess("Marker column number", false));

        
            marker_value = this.cells[row-1][col-1].toggleMarker();
            if(marker_value == 0) {
                // אם הפעולה לא הצליחה כי המקום כבר חשוף לשחקן
                System.out.println("\nYou can't mark a visible stop as a mine! 😐\n");
            } else {
                this.marks += marker_value;
                marked = true;
            }
        } while (!marked);

        if (this.marks == this.size) {
            // אם כל כמות הפצצות סומנו, המשחק נגמר
            // בדיקה האם ניצחון או הפסד!
            if (checkWin()) {
                return "win";
            } else {
                return "lose";
            }
        }

        return "continue";
    }
    
    private boolean checkWin() {
       // בהנחה שמורץ במצב שבו ישנם סימונים כמספר הפצצות על הלוח,
       // מחזיק אמת אם השחקן צדק במשחק וניצח במשחק ושקר אם נפסל והפסיד

        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                // בודק עבור כל תא האם מסומן ולא פצצה, ואם מתקיים מחזיר שהמשחק הופסד
                if (this.cells[row][col].isMarked() && !this.cells[row][col].isBomb()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void markEndgame() {
        // הופך את המצב של כל הנקודות לנקודות בסיום המשחק
        // כשהן מציגות את הערך האמיתי שלהן - פצצה או לא

        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.cells[row][col].endgame();
            }
        }
    }

    private boolean markVisible (MineCell cell) {
        // יסמן את התא הנתון ויראה אותו בתור הבא
        // יוחזר שקר אם התא הוא פצצה!

        int cell_value;

        if(cell.isBomb()) {
            return false;
        }

        cell_value = cell.setVisible();

        if (cell_value == 0) {
            
            // אם הערך של התא הנוכחי הוא 0, נצטרך לחשוף יותר תאים מסביבו
            // כי כך המשחק עובד. נעשה זאת בעזרת רקורסיה!

            MineCell[] near_cells = this.get_nearby_cells(cell.get_row(), cell.get_col());
            
            for (int i = 0; i < near_cells.length; i++) {
                if (!near_cells[i].isVisible()) {
                    markVisible(near_cells[i]);
                }
            }
        }

        return true;
    }

    private boolean markVisible(int row, int col) {
        // יסמן את התא הנתון ויראה אותו בתור הבא
        // יוחזר שקר אם התא הוא פצצה!

        MineCell cell = this.cells[row][col];
        return this.markVisible(cell);
    }

    public boolean play() {
        // פעולת המשחק עצמו. תחזיר אמת אם המשתמש ניצח

        String player_last_input, row, col;
        boolean running_game = true;

        System.out.println(Minesweeper.design.rules(this.size)); // הדפסת ההוראות
        System.out.print("\n");
        System.out.print(design.input_request("Type anything and press enter to start"));
        input.next();

        // המשחק עצמו
        do {

            this.round++;

            // הדפסת המצב
            System.out.println(design.line(this.size*3));
            System.out.println(this.toString());

            // הדפסת חוקי המצב
            System.out.println(design.round_rules() + "\n");

            // קלט ראשוני
            String request = String.format("Row number \\ \"%s\"", Minesweeper.design.marking_keyword());
            player_last_input = this.scanUntilSuccess(request, true);

            if (player_last_input.equals(design.marking_keyword())) {
                // אם השחקן רוצה לסמן מוקש
                System.out.print("\n");

                String board_status = this.markBomb();

                if (board_status != "continue") {
                    
                    this.markEndgame();

                    System.out.println(design.line(this.size*3));
                    System.out.println(this.toString(false));

                    if (board_status == "lose") {
                        System.out.println(design.losing_to_false_bombs());
                    } else {
                        System.out.println(design.winning());
                    }

                    running_game = false;
                }
            } else {
                // אם השחקן משחק רגיל
                row = player_last_input;
                col = this.scanUntilSuccess("Column number", false);

                boolean success = this.markVisible(Integer.parseInt(row)-1, Integer.parseInt(col)-1);

                if (!success) {
                    // אם השחקן דרך על מוקש
                    System.out.println(design.losing_by_exploring_bomb());
                    running_game = false;
                }
            }

        } while (running_game);

        return true;
    }

}