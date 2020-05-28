package com.bh183.permana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 3;
    private final static String DATABASE_NAME = "db_music";
    private final static String TABLE_LAGU = "t_lagu";
    private final static String KEY_ID_LAGU = "ID_Lagu";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_ARTIST = "Artist";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_ALBUM = "Album";
    private final static String KEY_LIRIK_LAGU = "Lirik_Lagu";
    private final static String KEY_LINK = "Link";
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_LAGU = "CREATE TABLE " + TABLE_LAGU
                + "(" + KEY_ID_LAGU + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_ARTIST + " TEXT, "
                + KEY_GAMBAR + " TEXT, " + KEY_ALBUM + " TEXT, "
                + KEY_LIRIK_LAGU + " TEXT, " + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_LAGU);
        inisialisasiLaguAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_LAGU;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahLagu(Lagu dataLagu) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataLagu.getJudul());
        cv.put(KEY_ARTIST, dataLagu.getArtist());
        cv.put(KEY_GAMBAR, dataLagu.getGambar());
        cv.put(KEY_ALBUM, dataLagu.getAlbum());
        cv.put(KEY_LIRIK_LAGU, dataLagu.getLirikLagu());
        cv.put(KEY_LINK, dataLagu.getLink());

        db.insert(TABLE_LAGU, null, cv);
        db.close();
    }

    public void tambahLagu(Lagu dataLagu, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataLagu.getJudul());
        cv.put(KEY_ARTIST, dataLagu.getArtist());
        cv.put(KEY_GAMBAR, dataLagu.getGambar());
        cv.put(KEY_ALBUM, dataLagu.getAlbum());
        cv.put(KEY_LIRIK_LAGU, dataLagu.getLirikLagu());
        cv.put(KEY_LINK, dataLagu.getLink());

        db.insert(TABLE_LAGU, null, cv);
    }

    public void editLagu(Lagu dataLagu) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataLagu.getJudul());
        cv.put(KEY_ARTIST, dataLagu.getArtist());
        cv.put(KEY_GAMBAR, dataLagu.getGambar());
        cv.put(KEY_ALBUM, dataLagu.getAlbum());
        cv.put(KEY_LIRIK_LAGU, dataLagu.getLirikLagu());
        cv.put(KEY_LINK, dataLagu.getLink());

        db.update(TABLE_LAGU, cv, KEY_ID_LAGU + "=?", new String[]{String.valueOf(dataLagu.getIdLagu())});

        db.close();
    }

    public void hapusLagu(int idLagu) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LAGU, KEY_ID_LAGU + "=?", new String[]{String.valueOf(idLagu)});
        db.close();
    }

    public ArrayList<Lagu> getAllLagu() {
        ArrayList<Lagu> dataLagu = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LAGU;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Lagu tempLagu = new Lagu(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataLagu.add(tempLagu);
            } while (csr.moveToNext());
        }

        return dataLagu;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiLaguAwal(SQLiteDatabase db) {
        int idLagu = 0;

        //Data Lagu ke 1
        Lagu lagu1 = new Lagu(
                idLagu,
                "Sunshine",
                "The Panturas",
                storeImageFile(R.drawable.music1),
                "Mabuk Laut",
                "I know that you're not ready to see it goes down\n" +
                        "But don't worry 'cause it won't forever go\n" +
                        "I know that you still want to see\n" +
                        "Then tomorrow, I'll bring you here with me\n" +
                        "'Cause I know\n" +
                        "We're running out of time\n" +
                        "To see it going down today\n" +
                        "You know, that I'm still holding on your arm\n" +
                        "It's getting dark\n" +
                        "There's no light from above, you know\n" +
                        "You know, that you will always be my sunshine\n" +
                        "'Cause tonight, we'll be fine\n" +
                        "I know, that you're not ready to see it goes down\n" +
                        "But don't worry 'cause it won't forever go\n" +
                        "I know, that you still want to see\n" +
                        "Then tomorrow, I'll bring you here with me\n" +
                        "'Cause I know\n" +
                        "We're running out of time\n" +
                        "To see it going down today\n" +
                        "You know, that I'm still holding on your arm\n" +
                        "It's getting dark\n" +
                        "There's no light from above, you know\n" +
                        "You know, that you will always be my sunshine\n" +
                        "'Cause tonight, we'll be fine\n" +
                        "We're running out of time\n" +
                        "To see it going down today\n" +
                        "You know, that I'm still holding on your arm\n" +
                        "It's getting dark\n" +
                        "There's no light from above, you know\n" +
                        "You know, that you will always be my sunshine\n" +
                        "We're running out of time\n" +
                        "To see it going down today\n" +
                        "You know, that I'm still holding on your arm\n" +
                        "It's getting dark\n" +
                        "There's no light from above, you know\n" +
                        "You know, that you will always be my sunshine\n" +
                        "We're running out of time\n" +
                        "To see it going down today\n" +
                        "You know, that I'm still holding on your arm\n" +
                        "It's getting dark\n" +
                        "There's no light from above, you know\n" +
                        "You know, that you will always be my sunshine\n" +
                        "'Cause tonight, we'll be fine\n" +
                        "'Cause tonight, we'll be fine",
                "https://www.google.com/search?q=sunshine+the+panturas+lyrics&oq=sunshine+the+panturas+ly&aqs=chrome.1.69i57j0l2.6214j0j9&sourceid=chrome&ie=UTF-8"
        );

        tambahLagu(lagu1, db);
        idLagu++;

        // Data Lagu ke 2
        Lagu lagu2 = new Lagu(
                idLagu,
                "La la Lost You",
                "Niki, 88rising",
                storeImageFile(R.drawable.music2),
                "Head in the Cloud II",
                "While I'm on Sunset, are you on the subway?\n" +
                        "While I drive, are you gettin' on the L-train?\n" +
                        "I mean, Manhattan's nice, but so are Malibu nights\n" +
                        "You would know if you stayed\n" +
                        "You would know if you put up a fight\n" +
                        "Your toes turn blue in winter, I'm gettin' red, rum\n" +
                        "Does the trick for all of the things left unsaid, I'm\n" +
                        "Missin' our drunken 2 A.M. Strolls in K-Town\n" +
                        "Now you're chasing fake highs in the Upper West Side\n" +
                        "And fuckin' on Brooklyns in Brooklyn\n" +
                        "Your Chelseas in Chelsea\n" +
                        "Hope that eases the pain\n" +
                        "So you remember to miss me\n" +
                        "And you sold your car\n" +
                        "Now you walk for miles\n" +
                        "Bet your feet feel numb\n" +
                        "(Crosswalks in my mind are shaky, so please hold on tight)\n" +
                        "All my demons run wild\n" +
                        "All my demons have your smile\n" +
                        "In the city of angels\n" +
                        "In the city of angels\n" +
                        "Hope New York holds you\n" +
                        "Hope it holds you like I do\n" +
                        "While my demons stay faithful\n" +
                        "In the city of angels\n" +
                        "Summer's endin' now and the nights are coolin' down\n" +
                        "Remember last winter when we would drive around?\n" +
                        "Silverlake, Hollywood, pretty little white lies got me good\n" +
                        "Thought this was love, I was misunderstood, mm\n" +
                        "Feelin' low on the low, drivin' through NoHo\n" +
                        "If I'm honest, I'd call, but I'm trying to let go\n" +
                        "And I hope you're happy, livin' life in taxis\n" +
                        "But you'll always have me, you'll always have me\n" +
                        "All my demons run wild\n" +
                        "All my demons have your smile\n" +
                        "In the city of angels\n" +
                        "In the city of angels\n" +
                        "Hope New York holds you\n" +
                        "Hope it holds you like I do\n" +
                        "While my demons stay faithful\n" +
                        "In the city of angels",
                "https://www.google.com/search?q=lala+lost+you+lyrics&oq=lala+lost+you+lyrics&aqs=chrome.0.69i59j0l4.1079j0j9&sourceid=chrome&ie=UTF-8"
        );

        tambahLagu(lagu2, db);
        idLagu++;

        // Data Lagu ke 3
        Lagu lagu3 = new Lagu(
                idLagu,
                "So Far Away",
                "Avenged Sevenfold",
                storeImageFile(R.drawable.music3),
                "Nightmare",
                "Never feared for anything\n" +
                        "Never chained but never free\n" +
                        "A light that healed the broken heart\n" +
                        "With all that it could\n" +
                        "Lived a life so endlessly\n" +
                        "Saw beyond what others see\n" +
                        "I tried to heal your broken heart\n" +
                        "With all that I could\n" +
                        "Will you stay?\n" +
                        "Will you stay away forever?\n" +
                        "How do I live without the ones I love?\n" +
                        "Time still turns the pages of the book it's burned\n" +
                        "Place and time always on my mind\n" +
                        "I have so much to say but you're so far away\n" +
                        "Plans of what our futures hold\n" +
                        "Foolish lies of growing old\n" +
                        "It seems we're so invincible\n" +
                        "The truth is so cold\n" +
                        "A final song, a last request\n" +
                        "A perfect chapter laid at rest\n" +
                        "Now and then I try to find\n" +
                        "A place in my mind\n" +
                        "Where you can stay\n" +
                        "You can stay awake forever\n" +
                        "How do I live without the ones I love?\n" +
                        "Time still turns the pages of the book it's burned\n" +
                        "Place and time always on my mind\n" +
                        "I have so much to say but you're so far away\n" +
                        "Sleep tight I'm not afraid (not afraid)\n" +
                        "The ones that we love are here with me\n" +
                        "Lay away a place for me (place for me)\n" +
                        "'Cause as soon as I'm done I'll be on my way\n" +
                        "To live eternally\n" +
                        "How do I live without the ones I love?\n" +
                        "Time still turns the pages of the book it's burned\n" +
                        "Place and time always on my mind\n" +
                        "And the light you left remains but it's so hard to stay\n" +
                        "When I have so much to say and you're so far away\n" +
                        "I love you, you were ready, the pain is strong and urges rise\n" +
                        "But I'll see you when He let's me\n" +
                        "Your pain is gone, your hands untied\n" +
                        "So far away\n" +
                        "And I need you to know\n" +
                        "So far away\n" +
                        "And I need you to, need you to know",
                "https://www.google.com/search?q=so+far+away+lyrics&oq=so+far+away+ly&aqs=chrome.1.69i57j0l7.6063j0j9&sourceid=chrome&ie=UTF-8"
        );

        tambahLagu(lagu3, db);
        idLagu++;

        // Data Lagu ke 4
        Lagu lagu4 = new Lagu(
                idLagu,
                "Evaluasi",
                "Hindia",
                storeImageFile(R.drawable.music4),
                "Evaluasi",
                "Yang tak bisa terobati\n" +
                        "Biarlah\n" +
                        "Mengering sendiri\n" +
                        "Menghias tubuh dan\n" +
                        "Yang mengevaluasi\n" +
                        "Ragamu\n" +
                        "Hanya kau sendiri\n" +
                        "Mereka tak mampu\n" +
                        "Melewati yang telah kau lewati\n" +
                        "Tiap berganti hari\n" +
                        "Rintangan yang kau hadapi\n" +
                        "Masalah yang mengeruh\n" +
                        "Ho, perasaan yang rapuh\n" +
                        "Ini belum separuhnya\n" +
                        "Biasa saja\n" +
                        "Kamu tak apa\n" +
                        "Yang selalu ingin ambil peran\n" +
                        "Hanya berlomba menjadi lebih\n" +
                        "Sedih dari dirimu\n" +
                        "Muak dikesampingkan\n" +
                        "Disamakan\n" +
                        "Hatimu terluka, sempurna\n" +
                        "Masalah yang mengeruh\n" +
                        "Ho, perasaan yang rapuh\n" +
                        "Ini belum separuhnya\n" +
                        "Biasa saja\n" +
                        "Kamu tak apa\n" +
                        "Perjalanan yang jauh\n" +
                        "Kau bangun untuk bertaruh\n" +
                        "Hari belum selesai\n" +
                        "Biasa saja\n" +
                        "Kamu tak apa\n" +
                        "Bilas muka, gosok gigi, evaluasi\n" +
                        "Tidur sejenak menemui esok pagi\n" +
                        "Walau pedihku bersamamu kali ini\n" +
                        "Ku masih ingin melihatmu esok hari\n" +
                        "Bilas muka, gosok gigi, evaluasi\n" +
                        "Tidur sejenak menemui esok pagi\n" +
                        "Walau pedihku bersamamu kali ini\n" +
                        "Ku masih ingin melihatmu esok hari\n" +
                        "Bilas muka, gosok gigi, evaluasi\n" +
                        "Tidur sejenak menemui esok pagi\n" +
                        "Walau pedihku bersamamu kali ini\n" +
                        "Ku masih ingin melihatmu esok hari",
                "https://www.google.com/search?q=evaluasi+hindia+lyrics&oq=evaluasi+hindia+lyrics&aqs=chrome.0.69i59j0.1783j0j9&sourceid=chrome&ie=UTF-8"
        );

        tambahLagu(lagu4, db);
        idLagu++;

        // Data Lagu ke 5
        Lagu lagu5 = new Lagu(
                idLagu,
                "One Only",
                "Pamungkas",
                storeImageFile(R.drawable.music5),
                "Walk The Talk",
                "Oo, There you are\n" +
                        "Sittin' still all stripes and lonely\n" +
                        "Hidin', wishin', waitin'\n" +
                        "While I'm, Here I am\n" +
                        "Standin' still stare at you only\n" +
                        "Everythin' gets blurry\n" +
                        "All I want is just to stay\n" +
                        "You can't shake me\n" +
                        "I would never dare\n" +
                        "Let go\n" +
                        "Through the talkin' and the walkin'\n" +
                        "I will give you all my lovin'\n" +
                        "Start countin' all the days\n" +
                        "Forever I will stay with you\n" +
                        "With you one only you\n" +
                        "Go far and roam about\n" +
                        "Comeback and callin' out to me\n" +
                        "To me one only me\n" +
                        "Oo, I'm in love\n" +
                        "What did I do To deserve you\n" +
                        "You tell me what did i do\n" +
                        "To be with you, love\n" +
                        "To be the one you runnin' into\n" +
                        "When the days do come through\n" +
                        "All I want is just to stay\n" +
                        "You can't shake me\n" +
                        "I would never dare\n" +
                        "Let go\n" +
                        "Through the talkin' And the walkin'\n" +
                        "I will give you all my lovin'\n" +
                        "(All of my, all the good lovin')\n" +
                        "Start countin' all the days\n" +
                        "Forever I will stay with you\n" +
                        "With you one only you\n" +
                        "Go far and roam about\n" +
                        "Comeback and callin' out to me\n" +
                        "To me one only me\n" +
                        "Well I'm luckiest\n" +
                        "To be the one\n" +
                        "Be the one\n" +
                        "To get you, to get you, to get you\n" +
                        "Now well I'm happiest\n" +
                        "To found the one\n" +
                        "Found the one\n" +
                        "Found the one only kinda love\n" +
                        "Uuu\n" +
                        "Yeah\n" +
                        "Start countin' all the days\n" +
                        "Forever I will stay with you\n" +
                        "With you one only you\n" +
                        "Go far and roam about\n" +
                        "Comeback and callin' out to me\n" +
                        "To me one only me\n" +
                        "Oo Uu\n" +
                        "(I wanna follow you forever) Yeah\n" +
                        "(With you, one only you)\n" +
                        "Oo, I'm in love\n" +
                        "What did I do to deserve you\n" +
                        "You tell me what did I do",
                "https://www.google.com/search?q=one+only+lyrics&oq=&sourceid=chrome&ie=UTF-8"
        );

        tambahLagu(lagu5, db);
    }
}
