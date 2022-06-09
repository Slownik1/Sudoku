package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Cell[][] table;
    String input;
    TableLayout tl;
    private class Cell
    {
        Button bt;
        int value;
        boolean fixed;

        public Cell(int initValue, Context THIS)
        {
            value=initValue;

            if(value!=0)
                fixed=true;
            else
                fixed = false;
            bt=new Button(THIS);
            if(fixed)
                bt.setText(String.valueOf(value));
            else
                bt.setTextColor(Color.BLUE);

            bt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(fixed)
                        return;
                    value++;
                    if(value>9)
                        value=1;
                    bt.setText(String.valueOf(value));
                    if(!Correct())
                        Toast.makeText(getApplicationContext(),"Błąd!",Toast.LENGTH_SHORT).show();
                    else if (Completed())
                        Toast.makeText(getApplicationContext(),"Ukończono!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    boolean Completed()
    {
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<9; j++)
            {
                if(table[i][j].value==0)
                    return false;
            }
        }
        return true;
    }

    boolean Correct(int i1, int j1, int i2, int j2)
    {
        boolean[] seen=new boolean[10];
        for(int i=1; i<=0; i++)
        {
            seen[i]=false;
        }
        for(int i=i1; i<i2; i++)
        {
            for(int j=j1; j < j2; j++)
            {
                int value=table[i][j].value;
                if(value!=0)
                {
                    if(seen[value]) return false;
                    seen[value]=true;
                }
            }
        }
        return true;
    }

    boolean Correct()
    {
        for(int i=0; i<9; i++)
        {
            if(!Correct(i, 0, i+1, 9)) return false;
        }
        for(int j=0; j<9; j++)
        {
            if(!Correct(0, j, 9, j+1)) return false;
        }
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<3; j++) {
                if (!Correct(3 * i, 3 * j, 3 * i + 3, 3 * j + 3)) return false;
            }
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        input= "? ? ? 7 4 3 ? 6 ? " +
                "4 ? 1 8 6 5 9 ? ? " +
                "8 ? 6 1 9 ? 5 ? 3 " +
                "? 8 ? ? 5 ? 2 ? ? " +
                "6 1 ? 3 ? ? 4 ? 5 " +
                "? 4 9 2 ? 6 ? 3 8 " +
                "? ? 3 ? 2 4 1 8 ? " +
                "? ? 8 ? 7 1 ? 5 4 " +
                "1 ? 4 9 3 ? 6 7 ? ";
        String[] split = input.split(" ");

        table= new Cell[9][9];
        tl = new TableLayout(this);

        for(int i=0;i<9;i++)
        {
            TableRow tr = new TableRow(this);
            for(int j=0;j<9;j++)
            {
                String s = split[i*9+j];
                Character c = s.charAt(0);
                table[i][j]=new Cell(c=='?'?0:c-'0', this);
                tr.addView(table[i][j].bt);
            }
            tl.addView(tr);
        }
        tl.setShrinkAllColumns(true);
        tl.setStretchAllColumns(true);
        setContentView(tl);
    }
}
