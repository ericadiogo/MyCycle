package com.ericadiogo.mycycle;

import android.content.Context;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ReminderWorker extends Worker {
    private final Context context;

    public ReminderWorker(Context context, WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @Override
    public Result doWork() {
        Data inputData = getInputData();
        String title = inputData.getString("Title");
        String message = inputData.getString("Message");

        if (title != null && message != null) {
            new NotificationHelper(context).createNotification(title, message);
        }
        return Result.success();
    }
}
