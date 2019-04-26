package com.artirex.sutakip.Helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class TelefonArama {

    Context context;

    public TelefonArama(Context context)
    {
        this.context = context;
    }
    public void CallPhone(String numara)
    {
        //izin alınacak
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+numara));
        context.startActivity(intent);
    }
}
