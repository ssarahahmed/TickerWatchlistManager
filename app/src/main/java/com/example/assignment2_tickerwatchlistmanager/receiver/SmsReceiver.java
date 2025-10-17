package com.example.assignment2_tickerwatchlistmanager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.assignment2_tickerwatchlistmanager.MainActivity;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class SmsReceiver extends BroadcastReceiver {


        private boolean isValidTicker(String ticker) {
            List<String> validTickers = Arrays.asList("DIS", "BAC", "PTON", "MAIN", "NEE", "AAPL");
            return validTickers.contains(ticker);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                return;
            }
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus == null || pdus.length == 0) return;
            String format = bundle.getString("format");
            StringBuilder fullMessage = new StringBuilder();
            for (Object pdu : pdus) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu, format);
                fullMessage.append(sms.getMessageBody());
            }

            String message = fullMessage.toString().trim();
            Pattern pattern = Pattern.compile("Ticker:<<([A-Za-z]+)>>");
            Matcher matcher = pattern.matcher(message);

            Intent launchIntent = new Intent(context, MainActivity.class);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);


            if (matcher.find()) {
                String ticker = matcher.group(1).toUpperCase();
                if (isValidTicker(ticker)) {
                    launchIntent.putExtra("NEW_TICKER", ticker);
                } else {
                    launchIntent.putExtra("TOAST_MESSAGE", "Invalid ticker: " + ticker);
                }
            } else {
                launchIntent.putExtra("TOAST_MESSAGE", "No valid watchlist entry found");
            }

            context.startActivity(launchIntent);
        }
    }