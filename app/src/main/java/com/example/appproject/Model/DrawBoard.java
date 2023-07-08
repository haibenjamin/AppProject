package com.example.appproject.Model;
import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.view.View;

public class DrawBoard extends View {

    Paint paint = new Paint();


    public DrawBoard(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            }

        }
        paint.setColor(Color.BLACK);
        canvas.drawRect(100, 100, 300, 300, paint);
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(300, 100, 500, 300, paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(100, 100, 300, 300, paint);


    }

}