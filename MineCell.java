public class MineCell {
    // תא במשחק. יכול להיות פצצה או לא!

    private int row;  // מיקום התא בלוח
    private int col;

    private boolean bomb;  // האם פצצה או לא
    private int near_bombs_count;  // מספר הפצצות ליד התא
    
    private String status;  /* המצב של הנקודה
    מצב הנקוד יכול להיות אחד מארבעה:
    "marked" - סומן על ידי השחקן בתור פצצה
    "visible" - השחקן חשף את המקום
    "hidden" - השחקן עוד לא יודע מה מסתתר מאחורי התא
    "endgame" - מראה את המצב האמיתי, כלומר פצצה או לא
    המצב ההתחלתי הוא "hidden" */

    private static DesignConfig design = new DesignConfig();  // מקרא המשחק


    public MineCell(int row, int col) {

        // שמירת מיקום התא בלוח
        this.row = row;
        this.col = col;

        // הגדרת המשתנים של התא
        this.bomb = false;
        this.status = "hidden";
        this.near_bombs_count = -1;  // עדיין לא הוגדר, יוגדר כחלק מהגדרת הלוח
    }

    public int get_row() {
        // מחזיר את מספר השורה של התא בלוח
        return this.row;
    }

    public int get_col() {
        // מחזיר את מספר העמודה של התא בלוח
        return this.col;
    }

    public void updateNearBombCount(int count) {
        // פעולה המעדכנת את מספר הפצצות ליד התא
        this.near_bombs_count = count;
    }

    public int toggleMarker() {
        // פעולה התנסה לסמן או להוריד סימון של פצצה
        // במיקום הנתון. אם נוסף סימון, יוחזר 1 חיובי,
        // אם סימן הוסר יוחזר 1 שלילי, ואם הפעולה לא בוצעה
        // בהצלחה יוחזר 0

        switch (this.status) {
            case "hidden":
                this.status = "marked";
                return 1;
            case "marked":
                this.status = "hidden";
                return -1;
            default:
                return 0;
        }
    }

    public void endgame() {
        // הופך את המצב של הנקודה לנקודה בסוף המשחק.
        this.status = "endgame";
    }

    public void setBomb() {
        // מטיל פצצה על התא
        this.bomb = true;
    }

    public boolean isBomb () {
        // מחזיר אמת אם התא הוא פצצה
        return this.bomb;
    }

    public boolean isMarked() {
        // יחזיר אמת אם התא נמצא במצב מסומן
        return this.status.equals("marked");
    }

    public int setVisible() {
        // הופך את מצב התא לגלוי, מציג את הערך שלו ומחזיר אותו
        if (this.status.equals("hidden")) {
            this.status = "visible";
        }
        return this.near_bombs_count;
    }

    public boolean isVisible() {
        // מחזיר אמת אם התא כבר נחשף על ידי המשתמש
        return this.status.equals("visible");
    }

    public String toString() {
        // פעולה התחזיר את הסימון של התא

        switch (this.status) {

            case "marked":
                return MineCell.design.marked();

            case "visible":
                if (this.near_bombs_count == 0) {
                    return MineCell.design.empty();
                }
                return MineCell.numToEmoji(this.near_bombs_count);
            
            case "endgame":
                if (this.bomb) {
                    return MineCell.design.bomb();
                }
                return MineCell.design.clear();
            
            case "hidden": default:
                return MineCell.design.hidden();

        }
    }

    private static String numToEmoji(int num) {
        // פונקציה התיקח מספר ותמיר אותו לאימוגי
        // משומש בפונקציות ההדפסה

        switch (num) {
            case 0:
                return " ";
            case 1:
                return "➀";
            case 2:
                return "➁";
            case 3:
                return "➂";
            case 4:
                return "➃";
            case 5:
                return "➄";
            case 6:
                return "➅";
            case 7:
                return "➆";
            case 8:
                return "➇";
            case 9:
                return "➈";
            case 10:
                return "➉";
        }

        return Integer.toString(num);  // אם המספר הוא לא תו אחד
    }

}