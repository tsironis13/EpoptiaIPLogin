package epoptia.iplogin.com.keyboard;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import epoptia.iplogin.com.R;

public class MyKeyboard extends LinearLayout implements View.OnClickListener{

    // constructors
    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private LinearLayout greekUpperLettersLlt;
    private LinearLayout latinUpperLettersLlt;
    private LinearLayout greekLowerLettersLlt;
    private LinearLayout latinLowerLettersLlt;
    private LinearLayout upperCaseLettersLlt;
    private LinearLayout lowerCaseLettersLlt;

    // keyboard keys (buttons)
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButton0;
    private ImageButton mButtonDelete;
    private Button mButtonEnter;
    private ImageButton mButtonChangeLanguage;
    private ImageButton mButtonAllCaprs;
    private Button mButtonFullStop;

    //region Upper Greek letters (buttons)

    private Button mButtonQuestionMark;
    private Button mButtonGreekUpperW;
    private Button mButtonGreekUpperE;
    private Button mButtonGreekUpperR;
    private Button mButtonGreekUpperT;
    private Button mButtonGreekUpperY;
    private Button mButtonGreekUpperU;
    private Button mButtonGreekUpperI;
    private Button mButtonGreekUpperO;
    private Button mButtonGreekUpperP;
    private Button mButtonGreekUpperA;
    private Button mButtonGreekUpperS;
    private Button mButtonGreekUpperD;
    private Button mButtonGreekUpperF;
    private Button mButtonGreekUpperG;
    private Button mButtonGreekUpperH;
    private Button mButtonGreekUpperJ;
    private Button mButtonGreekUpperK;
    private Button mButtonGreekUpperL;
    private Button mButtonGreekUpperZ;
    private Button mButtonGreekUpperX;
    private Button mButtonGreekUpperC;
    private Button mButtonGreekUpperV;
    private Button mButtonGreekUpperB;
    private Button mButtonGreekUpperN;
    private Button mButtonGreekUpperM;

    //endregion

    //region Upper Latin letters (buttons)

    private Button mButtonLatinUpperQ;
    private Button mButtonLatinUpperW;
    private Button mButtonLatinUpperE;
    private Button mButtonLatinUpperR;
    private Button mButtonLatinUpperT;
    private Button mButtonLatinUpperY;
    private Button mButtonLatinUpperU;
    private Button mButtonLatinUpperI;
    private Button mButtonLatinUpperO;
    private Button mButtonLatinUpperP;
    private Button mButtonLatinUpperA;
    private Button mButtonLatinUpperS;
    private Button mButtonLatinUpperD;
    private Button mButtonLatinUpperF;
    private Button mButtonLatinUpperG;
    private Button mButtonLatinUpperH;
    private Button mButtonLatinUpperJ;
    private Button mButtonLatinUpperK;
    private Button mButtonLatinUpperL;
    private Button mButtonLatinUpperZ;
    private Button mButtonLatinUpperX;
    private Button mButtonLatinUpperC;
    private Button mButtonLatinUpperV;
    private Button mButtonLatinUpperB;
    private Button mButtonLatinUpperN;
    private Button mButtonLatinUpperM;

    //endregion

    //region Lower Greek letters (buttons)

    private Button mButtonLowerQuestionMark;
    private Button mButtonGreekLowerW;
    private Button mButtonGreekLowerE;
    private Button mButtonGreekLowerR;
    private Button mButtonGreekLowerT;
    private Button mButtonGreekLowerY;
    private Button mButtonGreekLowerU;
    private Button mButtonGreekLowerI;
    private Button mButtonGreekLowerO;
    private Button mButtonGreekLowerP;
    private Button mButtonGreekLowerA;
    private Button mButtonGreekLowerS;
    private Button mButtonGreekLowerD;
    private Button mButtonGreekLowerF;
    private Button mButtonGreekLowerG;
    private Button mButtonGreekLowerH;
    private Button mButtonGreekLowerJ;
    private Button mButtonGreekLowerK;
    private Button mButtonGreekLowerL;
    private Button mButtonGreekLowerZ;
    private Button mButtonGreekLowerX;
    private Button mButtonGreekLowerC;
    private Button mButtonGreekLowerV;
    private Button mButtonGreekLowerB;
    private Button mButtonGreekLowerN;
    private Button mButtonGreekLowerM;

    //endregion

    //region Lower Latin letters (buttons)

    private Button mButtonLatinLowerQ;
    private Button mButtonLatinLowerW;
    private Button mButtonLatinLowerE;
    private Button mButtonLatinLowerR;
    private Button mButtonLatinLowerT;
    private Button mButtonLatinLowerY;
    private Button mButtonLatinLowerU;
    private Button mButtonLatinLowerI;
    private Button mButtonLatinLowerO;
    private Button mButtonLatinLowerP;
    private Button mButtonLatinLowerA;
    private Button mButtonLatinLowerS;
    private Button mButtonLatinLowerD;
    private Button mButtonLatinLowerF;
    private Button mButtonLatinLowerG;
    private Button mButtonLatinLowerH;
    private Button mButtonLatinLowerJ;
    private Button mButtonLatinLowerK;
    private Button mButtonLatinLowerL;
    private Button mButtonLatinLowerZ;
    private Button mButtonLatinLowerX;
    private Button mButtonLatinLowerC;
    private Button mButtonLatinLowerV;
    private Button mButtonLatinLowerB;
    private Button mButtonLatinLowerN;
    private Button mButtonLatinLowerM;

    //endregion

    private Button mButtonSlash;

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);

        greekUpperLettersLlt = (LinearLayout) findViewById(R.id.greekUpperLettersLlt);
        greekLowerLettersLlt = (LinearLayout) findViewById(R.id.greekLowerLettersLlt);
        latinLowerLettersLlt = (LinearLayout) findViewById(R.id.latinLowerLettersLlt);
        latinUpperLettersLlt = (LinearLayout) findViewById(R.id.latinUpperLettersLlt);
        //upperCaseLettersLlt = (LinearLayout) findViewById(R.id.lowerCaseLettersLlt);
        //lowerCaseLettersLlt = (LinearLayout)  findViewById(R.id.lowerCaseLettersLlt);

        mButton1 = (Button) findViewById(R.id.button_1);
        mButton2 = (Button) findViewById(R.id.button_2);
        mButton3 = (Button) findViewById(R.id.button_3);
        mButton4 = (Button) findViewById(R.id.button_4);
        mButton5 = (Button) findViewById(R.id.button_5);
        mButton6 = (Button) findViewById(R.id.button_6);
        mButton7 = (Button) findViewById(R.id.button_7);
        mButton8 = (Button) findViewById(R.id.button_8);
        mButton9 = (Button) findViewById(R.id.button_9);
        mButton0 = (Button) findViewById(R.id.button_0);
        mButtonDelete = (ImageButton) findViewById(R.id.button_delete);
        mButtonEnter = (Button) findViewById(R.id.button_enter);
        mButtonChangeLanguage = (ImageButton) findViewById(R.id.changeLanguageBtn);
        mButtonAllCaprs = (ImageButton) findViewById(R.id.capsBtn);
        mButtonFullStop = (Button) findViewById(R.id.button_fullstop);

        mButtonSlash = (Button) findViewById(R.id.button_slash);

        mButtonQuestionMark = (Button) findViewById(R.id.button_questionmark);
        mButtonGreekUpperW = (Button) findViewById(R.id.button_greeks);
        mButtonGreekUpperE = (Button) findViewById(R.id.button_greekE);
        mButtonGreekUpperR = (Button) findViewById(R.id.button_greekR);
        mButtonGreekUpperT = (Button) findViewById(R.id.button_greekT);
        mButtonGreekUpperY = (Button) findViewById(R.id.button_greekY);
        mButtonGreekUpperU = (Button) findViewById(R.id.button_greekU);
        mButtonGreekUpperI = (Button) findViewById(R.id.button_greekI);
        mButtonGreekUpperO = (Button) findViewById(R.id.button_greekO);
        mButtonGreekUpperP = (Button) findViewById(R.id.button_greekP);
        mButtonGreekUpperA = (Button) findViewById(R.id.button_greekA);
        mButtonGreekUpperS = (Button) findViewById(R.id.button_greekS);
        mButtonGreekUpperD = (Button) findViewById(R.id.button_greekD);
        mButtonGreekUpperF = (Button) findViewById(R.id.button_greekF);
        mButtonGreekUpperG = (Button) findViewById(R.id.button_greekG);
        mButtonGreekUpperH = (Button) findViewById(R.id.button_greekH);
        mButtonGreekUpperJ = (Button) findViewById(R.id.button_greekJ);
        mButtonGreekUpperK = (Button) findViewById(R.id.button_greekK);
        mButtonGreekUpperL = (Button) findViewById(R.id.button_greekL);
        mButtonGreekUpperZ = (Button) findViewById(R.id.button_greekZ);
        mButtonGreekUpperX = (Button) findViewById(R.id.button_greekX);
        mButtonGreekUpperC = (Button) findViewById(R.id.button_greekC);
        mButtonGreekUpperV = (Button) findViewById(R.id.button_greekV);
        mButtonGreekUpperB = (Button) findViewById(R.id.button_greekB);
        mButtonGreekUpperN = (Button) findViewById(R.id.button_greekN);
        mButtonGreekUpperM = (Button) findViewById(R.id.button_greekM);


        mButtonLatinUpperQ = (Button) findViewById(R.id.button_Q);
        mButtonLatinUpperW = (Button) findViewById(R.id.button_W);
        mButtonLatinUpperE = (Button) findViewById(R.id.button_E);
        mButtonLatinUpperR = (Button) findViewById(R.id.button_R);
        mButtonLatinUpperT = (Button) findViewById(R.id.button_T);
        mButtonLatinUpperY = (Button) findViewById(R.id.button_Y);
        mButtonLatinUpperU = (Button) findViewById(R.id.button_U);
        mButtonLatinUpperI = (Button) findViewById(R.id.button_I);
        mButtonLatinUpperO = (Button) findViewById(R.id.button_O);
        mButtonLatinUpperP = (Button) findViewById(R.id.button_P);
        mButtonLatinUpperA = (Button) findViewById(R.id.button_A);
        mButtonLatinUpperS = (Button) findViewById(R.id.button_S);
        mButtonLatinUpperD = (Button) findViewById(R.id.button_D);
        mButtonLatinUpperF = (Button) findViewById(R.id.button_F);
        mButtonLatinUpperG = (Button) findViewById(R.id.button_G);
        mButtonLatinUpperH = (Button) findViewById(R.id.button_H);
        mButtonLatinUpperJ = (Button) findViewById(R.id.button_J);
        mButtonLatinUpperK = (Button) findViewById(R.id.button_K);
        mButtonLatinUpperL = (Button) findViewById(R.id.button_L);
        mButtonLatinUpperZ = (Button) findViewById(R.id.button_Z);
        mButtonLatinUpperX = (Button) findViewById(R.id.button_X);
        mButtonLatinUpperC = (Button) findViewById(R.id.button_C);
        mButtonLatinUpperV = (Button) findViewById(R.id.button_V);
        mButtonLatinUpperB = (Button) findViewById(R.id.button_B);
        mButtonLatinUpperN = (Button) findViewById(R.id.button_N);
        mButtonLatinUpperM = (Button) findViewById(R.id.button_M);


        mButtonLowerQuestionMark = (Button) findViewById(R.id.button_lower_questionmark);
        mButtonGreekLowerW = (Button) findViewById(R.id.button_lower_greeks);
        mButtonGreekLowerE = (Button) findViewById(R.id.button_lower_greekE);
        mButtonGreekLowerR = (Button) findViewById(R.id.button_lower_greekR);
        mButtonGreekLowerT = (Button) findViewById(R.id.button_lower_greekT);
        mButtonGreekLowerY = (Button) findViewById(R.id.button_lower_greekY);
        mButtonGreekLowerU = (Button) findViewById(R.id.button_lower_greekU);
        mButtonGreekLowerI = (Button) findViewById(R.id.button_lower_greekI);
        mButtonGreekLowerO = (Button) findViewById(R.id.button_lower_greekO);
        mButtonGreekLowerP = (Button) findViewById(R.id.button_lower_greekP);
        mButtonGreekLowerA = (Button) findViewById(R.id.button_lower_greekA);
        mButtonGreekLowerS = (Button) findViewById(R.id.button_lower_greekS);
        mButtonGreekLowerD = (Button) findViewById(R.id.button_lower_greekD);
        mButtonGreekLowerF = (Button) findViewById(R.id.button_lower_greekF);
        mButtonGreekLowerG = (Button) findViewById(R.id.button_lower_greekG);
        mButtonGreekLowerH = (Button) findViewById(R.id.button_lower_greekH);
        mButtonGreekLowerJ = (Button) findViewById(R.id.button_lower_greekJ);
        mButtonGreekLowerK = (Button) findViewById(R.id.button_lower_greekK);
        mButtonGreekLowerL = (Button) findViewById(R.id.button_lower_greekL);
        mButtonGreekLowerZ = (Button) findViewById(R.id.button_lower_greekZ);
        mButtonGreekLowerX = (Button) findViewById(R.id.button_lower_greekX);
        mButtonGreekLowerC = (Button) findViewById(R.id.button_lower_greekC);
        mButtonGreekLowerV = (Button) findViewById(R.id.button_lower_greekV);
        mButtonGreekLowerB = (Button) findViewById(R.id.button_lower_greekB);
        mButtonGreekLowerN = (Button) findViewById(R.id.button_lower_greekN);
        mButtonGreekLowerM = (Button) findViewById(R.id.button_lower_greekM);


        mButtonLatinLowerQ = (Button) findViewById(R.id.button_q);
        mButtonLatinLowerW = (Button) findViewById(R.id.button_w);
        mButtonLatinLowerE = (Button) findViewById(R.id.button_e);
        mButtonLatinLowerR = (Button) findViewById(R.id.button_r);
        mButtonLatinLowerT = (Button) findViewById(R.id.button_t);
        mButtonLatinLowerY = (Button) findViewById(R.id.button_y);
        mButtonLatinLowerU = (Button) findViewById(R.id.button_u);
        mButtonLatinLowerI = (Button) findViewById(R.id.button_i);
        mButtonLatinLowerO = (Button) findViewById(R.id.button_o);
        mButtonLatinLowerP = (Button) findViewById(R.id.button_p);
        mButtonLatinLowerA = (Button) findViewById(R.id.button_a);
        mButtonLatinLowerS = (Button) findViewById(R.id.button_s);
        mButtonLatinLowerD = (Button) findViewById(R.id.button_d);
        mButtonLatinLowerF = (Button) findViewById(R.id.button_f);
        mButtonLatinLowerG = (Button) findViewById(R.id.button_g);
        mButtonLatinLowerH = (Button) findViewById(R.id.button_h);
        mButtonLatinLowerJ = (Button) findViewById(R.id.button_j);
        mButtonLatinLowerK = (Button) findViewById(R.id.button_k);
        mButtonLatinLowerL = (Button) findViewById(R.id.button_l);
        mButtonLatinLowerZ = (Button) findViewById(R.id.button_z);
        mButtonLatinLowerX = (Button) findViewById(R.id.button_x);
        mButtonLatinLowerC = (Button) findViewById(R.id.button_c);
        mButtonLatinLowerV = (Button) findViewById(R.id.button_v);
        mButtonLatinLowerB = (Button) findViewById(R.id.button_b);
        mButtonLatinLowerN = (Button) findViewById(R.id.button_n);
        mButtonLatinLowerM = (Button) findViewById(R.id.button_m);

        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);
        mButton7.setOnClickListener(this);
        mButton8.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonEnter.setOnClickListener(this);
        mButtonAllCaprs.setOnClickListener(this);
        mButtonChangeLanguage.setOnClickListener(this);
        mButtonFullStop.setOnClickListener(this);
        mButtonSlash.setOnClickListener(this);

        mButtonQuestionMark.setOnClickListener(this);
        mButtonGreekUpperW.setOnClickListener(this);
        mButtonGreekUpperE.setOnClickListener(this);
        mButtonGreekUpperR.setOnClickListener(this);
        mButtonGreekUpperT.setOnClickListener(this);
        mButtonGreekUpperY.setOnClickListener(this);
        mButtonGreekUpperU.setOnClickListener(this);
        mButtonGreekUpperI.setOnClickListener(this);
        mButtonGreekUpperO.setOnClickListener(this);
        mButtonGreekUpperP.setOnClickListener(this);
        mButtonGreekUpperA.setOnClickListener(this);
        mButtonGreekUpperS.setOnClickListener(this);
        mButtonGreekUpperD.setOnClickListener(this);
        mButtonGreekUpperF.setOnClickListener(this);
        mButtonGreekUpperG.setOnClickListener(this);
        mButtonGreekUpperH.setOnClickListener(this);
        mButtonGreekUpperJ.setOnClickListener(this);
        mButtonGreekUpperK.setOnClickListener(this);
        mButtonGreekUpperL.setOnClickListener(this);
        mButtonGreekUpperZ.setOnClickListener(this);
        mButtonGreekUpperX.setOnClickListener(this);
        mButtonGreekUpperC.setOnClickListener(this);
        mButtonGreekUpperV.setOnClickListener(this);
        mButtonGreekUpperB.setOnClickListener(this);
        mButtonGreekUpperN.setOnClickListener(this);
        mButtonGreekUpperM.setOnClickListener(this);

        mButtonLatinUpperQ.setOnClickListener(this);
        mButtonLatinUpperW.setOnClickListener(this);
        mButtonLatinUpperE.setOnClickListener(this);
        mButtonLatinUpperR.setOnClickListener(this);
        mButtonLatinUpperT.setOnClickListener(this);
        mButtonLatinUpperY.setOnClickListener(this);
        mButtonLatinUpperU.setOnClickListener(this);
        mButtonLatinUpperI.setOnClickListener(this);
        mButtonLatinUpperO.setOnClickListener(this);
        mButtonLatinUpperP.setOnClickListener(this);
        mButtonLatinUpperA.setOnClickListener(this);
        mButtonLatinUpperS.setOnClickListener(this);
        mButtonLatinUpperD.setOnClickListener(this);
        mButtonLatinUpperF.setOnClickListener(this);
        mButtonLatinUpperG.setOnClickListener(this);
        mButtonLatinUpperH.setOnClickListener(this);
        mButtonLatinUpperJ.setOnClickListener(this);
        mButtonLatinUpperK.setOnClickListener(this);
        mButtonLatinUpperL.setOnClickListener(this);
        mButtonLatinUpperZ.setOnClickListener(this);
        mButtonLatinUpperX.setOnClickListener(this);
        mButtonLatinUpperC.setOnClickListener(this);
        mButtonLatinUpperV.setOnClickListener(this);
        mButtonLatinUpperB.setOnClickListener(this);
        mButtonLatinUpperN.setOnClickListener(this);
        mButtonLatinUpperM.setOnClickListener(this);


        mButtonLowerQuestionMark.setOnClickListener(this);
        mButtonGreekLowerW.setOnClickListener(this);
        mButtonGreekLowerE.setOnClickListener(this);
        mButtonGreekLowerR.setOnClickListener(this);
        mButtonGreekLowerT.setOnClickListener(this);
        mButtonGreekLowerY.setOnClickListener(this);
        mButtonGreekLowerU.setOnClickListener(this);
        mButtonGreekLowerI.setOnClickListener(this);
        mButtonGreekLowerO.setOnClickListener(this);
        mButtonGreekLowerP.setOnClickListener(this);
        mButtonGreekLowerA.setOnClickListener(this);
        mButtonGreekLowerS.setOnClickListener(this);
        mButtonGreekLowerD.setOnClickListener(this);
        mButtonGreekLowerF.setOnClickListener(this);
        mButtonGreekLowerG.setOnClickListener(this);
        mButtonGreekLowerH.setOnClickListener(this);
        mButtonGreekLowerJ.setOnClickListener(this);
        mButtonGreekLowerK.setOnClickListener(this);
        mButtonGreekLowerL.setOnClickListener(this);
        mButtonGreekLowerZ.setOnClickListener(this);
        mButtonGreekLowerX.setOnClickListener(this);
        mButtonGreekLowerC.setOnClickListener(this);
        mButtonGreekLowerV.setOnClickListener(this);
        mButtonGreekLowerB.setOnClickListener(this);
        mButtonGreekLowerN.setOnClickListener(this);
        mButtonGreekLowerM.setOnClickListener(this);

        mButtonLatinLowerQ.setOnClickListener(this);
        mButtonLatinLowerW.setOnClickListener(this);
        mButtonLatinLowerE.setOnClickListener(this);
        mButtonLatinLowerR.setOnClickListener(this);
        mButtonLatinLowerT.setOnClickListener(this);
        mButtonLatinLowerY.setOnClickListener(this);
        mButtonLatinLowerU.setOnClickListener(this);
        mButtonLatinLowerI.setOnClickListener(this);
        mButtonLatinLowerO.setOnClickListener(this);
        mButtonLatinLowerP.setOnClickListener(this);
        mButtonLatinLowerA.setOnClickListener(this);
        mButtonLatinLowerS.setOnClickListener(this);
        mButtonLatinLowerD.setOnClickListener(this);
        mButtonLatinLowerF.setOnClickListener(this);
        mButtonLatinLowerG.setOnClickListener(this);
        mButtonLatinLowerH.setOnClickListener(this);
        mButtonLatinLowerJ.setOnClickListener(this);
        mButtonLatinLowerK.setOnClickListener(this);
        mButtonLatinLowerL.setOnClickListener(this);
        mButtonLatinLowerZ.setOnClickListener(this);
        mButtonLatinLowerX.setOnClickListener(this);
        mButtonLatinLowerC.setOnClickListener(this);
        mButtonLatinLowerV.setOnClickListener(this);
        mButtonLatinLowerB.setOnClickListener(this);
        mButtonLatinLowerN.setOnClickListener(this);
        mButtonLatinLowerM.setOnClickListener(this);

        keyValues.put(R.id.button_slash, "/");

        // map buttons IDs to input strings
        keyValues.put(R.id.button_1, "1");
        keyValues.put(R.id.button_2, "2");
        keyValues.put(R.id.button_3, "3");
        keyValues.put(R.id.button_4, "4");
        keyValues.put(R.id.button_5, "5");
        keyValues.put(R.id.button_6, "6");
        keyValues.put(R.id.button_7, "7");
        keyValues.put(R.id.button_8, "8");
        keyValues.put(R.id.button_9, "9");
        keyValues.put(R.id.button_0, "0");
        keyValues.put(R.id.button_enter, "\n");

        keyValues.put(R.id.button_lower_questionmark, ";");
        keyValues.put(R.id.button_questionmark, ";");

        keyValues.put(R.id.button_greeks, "ς");
        keyValues.put(R.id.button_greekE, "Ε");
        keyValues.put(R.id.button_greekR, "Ρ");
        keyValues.put(R.id.button_greekT, "Τ");
        keyValues.put(R.id.button_greekY, "Υ");
        keyValues.put(R.id.button_greekU, "Θ");
        keyValues.put(R.id.button_greekI, "Ι");
        keyValues.put(R.id.button_greekO, "Ο");
        keyValues.put(R.id.button_greekP, "Π");
        keyValues.put(R.id.button_greekA, "Α");
        keyValues.put(R.id.button_greekS, "Σ");
        keyValues.put(R.id.button_greekD, "Δ");
        keyValues.put(R.id.button_greekF, "Φ");
        keyValues.put(R.id.button_greekG, "Γ");
        keyValues.put(R.id.button_greekH, "Η");
        keyValues.put(R.id.button_greekJ, "Ξ");
        keyValues.put(R.id.button_greekK, "Κ");
        keyValues.put(R.id.button_greekL, "Λ");
        keyValues.put(R.id.button_greekZ, "Ζ");
        keyValues.put(R.id.button_greekX, "Χ");
        keyValues.put(R.id.button_greekC, "Ψ");
        keyValues.put(R.id.button_greekV, "Ω");
        keyValues.put(R.id.button_greekB, "Β");
        keyValues.put(R.id.button_greekN, "Ν");
        keyValues.put(R.id.button_greekM, "Μ");


        keyValues.put(R.id.button_Q, "Q");
        keyValues.put(R.id.button_W, "W");
        keyValues.put(R.id.button_E, "E");
        keyValues.put(R.id.button_R, "R");
        keyValues.put(R.id.button_T, "T");
        keyValues.put(R.id.button_Y, "Y");
        keyValues.put(R.id.button_U, "U");
        keyValues.put(R.id.button_I, "I");
        keyValues.put(R.id.button_O, "O");
        keyValues.put(R.id.button_P, "P");
        keyValues.put(R.id.button_A, "A");
        keyValues.put(R.id.button_S, "S");
        keyValues.put(R.id.button_D, "D");
        keyValues.put(R.id.button_F, "F");
        keyValues.put(R.id.button_G, "G");
        keyValues.put(R.id.button_H, "H");
        keyValues.put(R.id.button_J, "J");
        keyValues.put(R.id.button_K, "K");
        keyValues.put(R.id.button_L, "L");
        keyValues.put(R.id.button_Z, "Z");
        keyValues.put(R.id.button_X, "X");
        keyValues.put(R.id.button_C, "C");
        keyValues.put(R.id.button_V, "V");
        keyValues.put(R.id.button_B, "B");
        keyValues.put(R.id.button_N, "N");
        keyValues.put(R.id.button_M, "M");


        keyValues.put(R.id.button_lower_greeks, "ς");
        keyValues.put(R.id.button_lower_greekE, "ε");
        keyValues.put(R.id.button_lower_greekR, "ρ");
        keyValues.put(R.id.button_lower_greekT, "τ");
        keyValues.put(R.id.button_lower_greekY, "υ");
        keyValues.put(R.id.button_lower_greekU, "θ");
        keyValues.put(R.id.button_lower_greekI, "ι");
        keyValues.put(R.id.button_lower_greekO, "ο");
        keyValues.put(R.id.button_lower_greekP, "π");
        keyValues.put(R.id.button_lower_greekA, "α");
        keyValues.put(R.id.button_lower_greekS, "σ");
        keyValues.put(R.id.button_lower_greekD, "δ");
        keyValues.put(R.id.button_lower_greekF, "φ");
        keyValues.put(R.id.button_lower_greekG, "γ");
        keyValues.put(R.id.button_lower_greekH, "η");
        keyValues.put(R.id.button_lower_greekJ, "ξ");
        keyValues.put(R.id.button_lower_greekK, "κ");
        keyValues.put(R.id.button_lower_greekL, "λ");
        keyValues.put(R.id.button_lower_greekZ, "ζ");
        keyValues.put(R.id.button_lower_greekX, "χ");
        keyValues.put(R.id.button_lower_greekC, "ψ");
        keyValues.put(R.id.button_lower_greekV, "ω");
        keyValues.put(R.id.button_lower_greekB, "β");
        keyValues.put(R.id.button_lower_greekN, "ν");
        keyValues.put(R.id.button_lower_greekM, "μ");


        keyValues.put(R.id.button_q, "q");
        keyValues.put(R.id.button_w, "w");
        keyValues.put(R.id.button_e, "e");
        keyValues.put(R.id.button_r, "r");
        keyValues.put(R.id.button_t, "t");
        keyValues.put(R.id.button_y, "y");
        keyValues.put(R.id.button_u, "u");
        keyValues.put(R.id.button_i, "i");
        keyValues.put(R.id.button_o, "o");
        keyValues.put(R.id.button_p, "p");
        keyValues.put(R.id.button_a, "a");
        keyValues.put(R.id.button_s, "s");
        keyValues.put(R.id.button_d, "d");
        keyValues.put(R.id.button_f, "f");
        keyValues.put(R.id.button_g, "g");
        keyValues.put(R.id.button_h, "h");
        keyValues.put(R.id.button_j, "j");
        keyValues.put(R.id.button_k, "k");
        keyValues.put(R.id.button_l, "l");
        keyValues.put(R.id.button_z, "z");
        keyValues.put(R.id.button_x, "x");
        keyValues.put(R.id.button_c, "c");
        keyValues.put(R.id.button_v, "v");
        keyValues.put(R.id.button_b, "b");
        keyValues.put(R.id.button_n, "n");
        keyValues.put(R.id.button_m, "m");

        keyValues.put(R.id.button_fullstop, ".");
    }

    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (this.inputConnection == null) return;

        if (v.getId() == R.id.changeLanguageBtn) {
            if (greekLowerLettersLlt.getVisibility() == VISIBLE || greekUpperLettersLlt.getVisibility() == VISIBLE) {
                greekLowerLettersLlt.setVisibility(GONE);
                greekUpperLettersLlt.setVisibility(GONE);

                latinLowerLettersLlt.setVisibility(VISIBLE);

                return;
            }

            if (latinLowerLettersLlt.getVisibility() == VISIBLE || latinUpperLettersLlt.getVisibility() == VISIBLE) {
                latinLowerLettersLlt.setVisibility(GONE);
                latinUpperLettersLlt.setVisibility(GONE);

                greekLowerLettersLlt.setVisibility(VISIBLE);

                return;
            }

            return;
        }

        if (v.getId() == R.id.capsBtn) {
            if (greekLowerLettersLlt.getVisibility() == VISIBLE) {
                //Log.e("test", "greek lower is visible");

                greekLowerLettersLlt.setVisibility(GONE);
                greekUpperLettersLlt.setVisibility(VISIBLE);

                return;
            }

            if (latinLowerLettersLlt.getVisibility() == VISIBLE) {
                //Log.e("test", "latin lower is visible");

                latinLowerLettersLlt.setVisibility(GONE);
                latinUpperLettersLlt.setVisibility(VISIBLE);

                return;
            }

            if (greekUpperLettersLlt.getVisibility() == VISIBLE) {
                //Log.e("test", "greek upper is visible");

                greekUpperLettersLlt.setVisibility(GONE);
                greekLowerLettersLlt.setVisibility(VISIBLE);

                return;
            }

            if (latinUpperLettersLlt.getVisibility() == VISIBLE) {
                //Log.e("test", "latin upper is visible");

                latinUpperLettersLlt.setVisibility(GONE);
                latinLowerLettersLlt.setVisibility(VISIBLE);

                return;
            }


            return;
        }

        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.button_delete) {
            CharSequence selectedText = this.inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                this.inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                this.inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(v.getId());
            this.inputConnection.commitText(value, 1);
        }
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }

}
