package com.example.myquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myquiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        final String SQL_CREATE_STUDENT_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLESTUDENT_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_NAME + " TEXT, " +
                QuestionsTable.COLUMN_MARK + " INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_STUDENT_TABLE);

        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuestionsTable() {
        Question q1 = new Question("Что из этого в настоящее время не поддерживается в Kotlin?", "A) JVM;", "B) JavaScript;", "C) LLVM;","D) .NET CLR", 4);
        addQuestion(q1);
        Question q2 = new Question("Какое из объявлений функций является валидным?", "A) int sum(int a, int b)", "B) int sum(a : Int, b : Int)", "C) function sum(a : Int, b : Int) : Int","D) fun sum(a : Int, b : Int) : Int", 4);
        addQuestion(q2);
        Question q3 = new Question("Чего не предполагает data-class?", "A) Авто-генерируемый метод toString()", "B) метод copy(...) для создания копии экземпляров", "C) Автоматическое преобразования из/в JSON","D) Автогенерируемые методы hashCode() и equals()", 3);
        addQuestion(q3);
        Question q4 = new Question("Как в kotlin объявить целочисленную переменную?", "A) var i : int = 42", "B) let i = 42", "C) int i = 42","D) var i : Int = 42", 4);
        addQuestion(q4);
        Question q5 = new Question("Укажите как привести строку к int", "A) val i : Int = <Int>\"10\"", "B) val i : Int = Int.parseInt(\"10\")", "C) var i : Int = \"10\".toInt()","D) var i : Int = (Int)\"10\"",3);
        addQuestion(q5);
        Question q6 = new Question("Какой тип имеет arr в коде ниже: \n val arr = arrayOf(1,2,3)", "A) IntArray", "B) Int[]", "C) Array<Int>","D) int[]",3);
        addQuestion(q6);
        Question q7 = new Question("Что такое to в коде ниже: \n val test = 10 to 20", "A) Инфиксная функция, создающая пару (10,20)", "B) Ключевое слово Kotlin для создания пары (10,20)", "C) Ключевое слова для создания диапазона от 10 до 20","D) Опечатка",1);
        addQuestion(q7);
        Question q8 = new Question("Для чего нужен оператор !! ?", "A) Это оператор модуля, аналог %", "B) Сравнивает 2 занчения на тождественность", "C) Преобразует любое значение в ненулевой тип и генерирует исключение, если значение = null ","D) Правильного ответа нет",3);
        addQuestion(q8);
        Question q9 = new Question("Что делает нижеприведенный код: \n foo()()", "A) Создает двумерный массив", "B) Не скомпилируется", "C) Вызывает асинхронно foo","D) Вызывает функцию, которая вернется после вызова foo",4);
        addQuestion(q9);
        Question q10 = new Question("Совместим ли Kotlin с Java?", "A) Да, совместим", "B) Не может взаимодейстовать с Java", "C) Совместим на уровне рантайма","D) Пока Kotlin запущен в JVM, он не может взаимодействовать с Java",1);
        addQuestion(q10);
        Question q11 = new Question("По какой лицензии доступен Kotlin?", "A) Apache 2", "B) GPL", "C) MIT","D) Adobe",1);
        addQuestion(q11);
        Question q12 = new Question("Какая функция служит для вывода данных?", "A) print()", "B) log()", "C) system.out()","D) write()",1);
        addQuestion(q12);
        Question q13 = new Question("Какая функция является основной в Kotlin?", "A) run()", "B) start()", "C) main()","D) Main()",3);
        addQuestion(q13);
        Question q14 = new Question("По умолчанию, классы в Kotlin являются", "A) Публичными", "B) Абстрактными", "C) Приватными","D) Финализированными",4);
        addQuestion(q14);
        Question q15 = new Question("Каких типов данных нет в Kotlin?", "A) TEXT", "B) BLOB", "C) BOOLEAN","D) String",12);
        addQuestion(q15);
        Question q16 = new Question("Как объявить переменную в Kotlin?", "A) с помощью ключевого слова variable", "B) с помощью ключевого слова var", "C) с помощью ключевого слова val","D) с помощью ключевого слова const",23);
        addQuestion(q16);
        Question q17 = new Question("Какие модификаторы доступа может иметь класс?", "A) External", "B) Internal", "C) Sealed","D) Public",24);
        addQuestion(q17);
        Question q18 = new Question("Image2", "A) [1, 2, 3, 4]", "B) True", "C) False","D) [1, 2, 3]",2);
        addQuestion(q18);
        Question q19 = new Question("Image1", "A) a является volatile", "B) b является final и не может быть изменено", "C) a является final и не может быть изменено","D) b никогда не сможет стать null",4);
        addQuestion(q19);
        Question q20 = new Question("Image3", "A) True", "B) False", "C) Не скомпилируется","D) Будет сгенерировано исключение",2);
        addQuestion(q20);
    }
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public void addStudentResult(StudentResult sr) {
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_NAME, sr.getName());
        cv.put(QuestionsTable.COLUMN_MARK, sr.getMark());
        dbb.insert(QuestionsTable.TABLESTUDENT_NAME, null, cv);
    }
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}