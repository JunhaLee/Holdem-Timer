package com.example.myapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {
    private var timerTask: Timer? = null
    var cur_time = 600
    var timeTick = 600
    var totalTime = 0
    var totalHour = 0
    var totalMinute = 0
    var totalSecond = 0
    var breakTime = 50
    var breakHour = 0
    var breakMinute = 49
    var breakSecond = 0
    var minute = 10
    var second = 0
    var count = 0
    var level = 1
    private var isRunning = false
    var curVal = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ver10.isEnabled = false
        ver7.isEnabled = true
        ver10.setOnClickListener {
            timerTask?.cancel();
            curVal = 10
            level = 1
            text1.setText("TIME FOR NEXT BLIND")
            levelText.setText("Level")
            levelVal.text = String.format("%d", level - (level / 6))
            cur_time = 600
            minute = 10
            second = 0
            totalTime = 0
            totalHour = 0
            totalMinute = 0
            totalSecond = 0
            breakTime = 50
            breakHour = 0
            breakMinute = 50
            breakSecond = 0
            breaktimenum.text =
                    String.format("%02d : %02d : %02d", breakHour, breakMinute, breakSecond)
            totaltimenum.text =
                    String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
            time.text = String.format("%02d : %02d", minute, second)
            ver10.isEnabled = false
            ver7.isEnabled = true
            reset()
            downControl()
            ver10.setTextColor(Color.parseColor("#B57910"))
            ver7.setTextColor(Color.parseColor("#808080"))
            box.setBackgroundColor(Color.parseColor("#000000"))
            imageView1.setImageResource(R.drawable.logo)
            levelval(1)
            anteval(1)
            start.setBackgroundColor(Color.parseColor("#000000"))
            reset.setBackgroundColor(Color.parseColor("#000000"))
            up.setBackgroundColor(Color.parseColor("#000000"))
            down.setBackgroundColor(Color.parseColor("#000000"))
            levelup.setBackgroundColor(Color.parseColor("#000000"))
            leveldown.setBackgroundColor(Color.parseColor("#000000"))
            ver10.setBackgroundColor(Color.parseColor("#000000"))
            ver7.setBackgroundColor(Color.parseColor("#000000"))
        }
        ver7.setOnClickListener {
            timerTask?.cancel();
            curVal = 7
            level = 1
            text1.setText("TIME FOR NEXT BLIND")
            levelText.setText("Level")
            levelVal.text = String.format("%d", level)
            cur_time = 420
            minute = 7
            second = 0
            totalTime = 0
            totalHour = 0
            totalMinute = 0
            totalSecond = 0
            breakTime = 35
            breakHour = 0
            breakMinute = 35
            breakSecond = 0
            breaktimenum.text =
                    String.format("-")
            totaltimenum.text =
                    String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
            time.text = String.format("%02d : %02d", minute, second)
            ver7.isEnabled = false
            ver10.isEnabled = true
            reset()
            downControl()
            ver7.setTextColor(Color.parseColor("#B57910"))
            ver10.setTextColor(Color.parseColor("#808080"))
            box.setBackgroundColor(Color.parseColor("#FFFFFF"))
            levelvalfor7(1)
            antevalfor7(1)
            imageView1.setImageResource(R.drawable.logo1)
            start.setBackgroundColor(Color.parseColor("#FAFAFA"))
            reset.setBackgroundColor(Color.parseColor("#FAFAFA"))
            up.setBackgroundColor(Color.parseColor("#FAFAFA"))
            down.setBackgroundColor(Color.parseColor("#FAFAFA"))
            levelup.setBackgroundColor(Color.parseColor("#FAFAFA"))
            leveldown.setBackgroundColor(Color.parseColor("#FAFAFA"))
            ver10.setBackgroundColor(Color.parseColor("#FAFAFA"))
            ver7.setBackgroundColor(Color.parseColor("#FAFAFA"))
        }

        start.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                start()
            }
            else pause()
            downControl()
        }
        reset.setOnClickListener {
            totalTime = (level - 1) * curVal * 60
            totalMinute = (level - 1) * curVal
            totalHour = totalMinute / 60
            totalMinute = totalMinute % 60
            totalSecond = 0


            if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23) {
                breakTime = curVal * 5
                breakSecond = 0
                breakMinute = curVal * 5
            } else if (level != 5 && level != 11 && level != 17 && level != 23) {
                breakTime = (curVal * 5) - (curVal * (((level - (level / 6)) % 5) - 1))
                breakSecond = 0
                breakMinute = (curVal * 5) - (curVal * (((level - (level / 6)) % 5) - 1))
            } else {
                breakTime = curVal
                breakSecond = 0
                breakMinute = curVal
            }

            levelVal.text = String.format("%d", level - (level / 6))
            totaltimenum.text =
                    String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
            breaktimenum.text =
                    String.format("%02d : %02d : %02d", breakHour, breakMinute, breakSecond)
            breakMinute--
            reset()
            downControl()
        }
        up.setOnClickListener {
            cur_time = cur_time + 60
            minute = minute + 1
            time.text = String.format("%02d : %02d", minute, second)
            downControl()
        }
        down.setOnClickListener {
            if (minute == 0)
                minute = 1
            cur_time = cur_time - 60
            minute = minute - 1
            time.text = String.format("%02d : %02d", minute, second)
            downControl()

        }
        levelup.setOnClickListener {
            if (curVal == 10)
                levelup()
            else
                levelupfor7()
            downControl()
        }
        leveldown.setOnClickListener {
            if (curVal == 10)
                leveldown()
            else
                leveldownfor7()
            downControl()
        }
    }



    private fun start() {
        val time: TextView = findViewById(R.id.time)
        var mediaplayer: MediaPlayer? = null
        start.setText("Stop")
        mediaplayer = MediaPlayer.create(this, R.raw.sound)
        if (curVal == 10) {
            if (breakSecond == 0)
                breakSecond = 60
            minute = cur_time / 60
            second = cur_time % 60
            downControl()
            if (breakMinute == curVal * 5)
                breakMinute = (curVal * 5) - 1
            timerTask = kotlin.concurrent.timer(period = 1000, initialDelay = 1000) {
                runOnUiThread {
                    if ((((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23))
                        breaktimenum.text = String.format("-")
                    else
                        breaktimenum.text =
                                String.format("%02d : %02d : %02d", breakHour, breakMinute, breakSecond)
                    totaltimenum.text =
                            String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
                    time.text = String.format("%02d : %02d", minute, second)

                }


                // totaltimenum.text = String.format("%02 : %02d : %02d", totalHour, totalMinute, totalSecond)

                if (second == 0 && minute == 0) {
                    mediaplayer?.start()
                    cur_time = 600
                    second = 0
                    minute = curVal
                    if (level == 23)
                        level--
                    level++
                    if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23) {
                        text1.setText("BREAK TIME")
                        levelText.setText("BREAK")
                        levelVal.setText("TIME")
                    } else {
                        text1.setText("TIME FOR NEXT BLIND")
                        levelText.setText("Level")
                        levelVal.text = String.format("%d", level - (level / 6))
                    }
                    levelVal.text = String.format("%d", level - (level / 6))
                    levelval(level)
                    anteval(level)
                }
                if (second == 0) {
                    minute--
                    second = 60
                }
                second--
                totalTime++
                totalSecond++
                if (totalSecond == 60) {
                    totalMinute++
                    totalSecond = 0
                    if (totalMinute == 60) {
                        totalMinute = 0
                        totalHour++
                    }
                }
                breakTime--
                breakSecond--
                if (breakSecond == 0) {
                    breakMinute--
                    breakSecond = 60
                }
                if (breakSecond == 0 && breakMinute == 0) {
                    breakSecond = 60
                    breakMinute = 50
                }
                cur_time--
                if (minute == 9 && second == 55) {
                    mediaplayer.stop()
                }


            }
        } else {
            minute = cur_time / 60
            second = cur_time % 60
            downControl()
            timerTask = kotlin.concurrent.timer(period = 1000, initialDelay = 1000) {
                runOnUiThread {

                    breaktimenum.text = String.format("-")

                    totaltimenum.text =
                            String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
                    time.text = String.format("%02d : %02d", minute, second)

                }


                // totaltimenum.text = String.format("%02 : %02d : %02d", totalHour, totalMinute, totalSecond)

                if (second == 0 && minute == 0) {
                    mediaplayer?.start()
                    cur_time = 420
                    second = 0
                    minute = curVal
                    if (level == 20)
                        level--
                    level++

                    text1.setText("TIME FOR NEXT BLIND")
                    levelText.setText("Level")
                    levelVal.text = String.format("%d", level)
                    levelvalfor7(level)
                    antevalfor7(level)
                }
                if (second == 0) {
                    minute--
                    second = 60
                }
                second--
                totalTime++
                totalSecond++
                if (totalSecond == 60) {
                    totalMinute++
                    totalSecond = 0
                    if (totalMinute == 60) {
                        totalMinute = 0
                        totalHour++
                    }
                }
                cur_time--
                if (minute == 9 && second == 55) {
                    mediaplayer.stop()
                }


            }


        }

    }

    private fun pause() {
        start.setText("Start")
        timerTask?.cancel();
    }

    private fun reset() {
        timerTask?.cancel()
        cur_time = curVal * 60
        timeTick = curVal * 60
        isRunning = false
        start.setText("Start")
        minute = curVal
        second = 0
        time.text = String.format("%02d : %02d", minute, second)
        if(curVal == 7)
            breaktimenum.text =
                    String.format("-")

    }

    private fun downControl() {
        if (minute != 0)
            down.isEnabled = true
        else
            down.isEnabled = false

    }

    private fun levelup() {
        if (level == 23)
            level--
        level++
        totalTime = (level - 1) * 60 * curVal
        totalMinute = (level - 1) * curVal
        totalHour = totalMinute / 60
        totalMinute = totalMinute % 60
        totalSecond = 0


        if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23) {
            breakTime = curVal * 5
            breakSecond = 0
            breakMinute = curVal * 5
        } else if (level != 5 && level != 11 && level != 17 && level != 23) {
            breakTime = (curVal * 5) - (curVal * (((level - (level / 6)) % 5) - 1))
            breakSecond = 0
            breakMinute = (curVal * 5) - (curVal * (((level - (level / 6)) % 5) - 1))
        } else {
            breakTime = curVal
            breakSecond = 0
            breakMinute = curVal
        }

        levelVal.text = String.format("%d", level - (level / 6))
        totaltimenum.text = String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
        if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23)
            breaktimenum.text = String.format("-")
        else
            breaktimenum.text =
                    String.format("%02d : %02d : %02d", breakHour, breakMinute, breakSecond)
        breakMinute--


        if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23) {
            text1.setText("BREAK TIME")
            levelText.setText("BREAK")
            levelVal.setText("TIME")
        } else {
            text1.setText("TIME FOR NEXT BLIND")
            levelText.setText("Level")
            levelVal.text = String.format("%d", level - (level / 6))
        }
        levelval(level)
        anteval(level)
        reset()

    }

    private fun levelupfor7() {
        if (level == 20)
            level--
        level++
        totalTime = (level - 1) * 60 * curVal
        totalMinute = (level - 1) * curVal
        totalHour = totalMinute / 60
        totalMinute = totalMinute % 60
        totalSecond = 0
        levelVal.text = String.format("%d", level)
        totaltimenum.text = String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
        breaktimenum.text = String.format("-")
        text1.setText("TIME FOR NEXT BLIND")
        levelText.setText("Level")
        levelVal.text = String.format("%d", level)
        levelvalfor7(level)
        antevalfor7(level)
        reset()

    }

    private fun leveldown() {
        totalTime = (level - 2) * 60 * curVal
        if (level == 1)
            totalMinute = (level - 1) * curVal
        else
            totalMinute = (level - 2) * curVal
        totalHour = totalMinute / 60
        totalMinute = totalMinute % 60
        totalSecond = 0

        if (level == 1)
            level++
        level--

        if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23) {
            breakTime = curVal * 5
            breakSecond = 0
            breakMinute = curVal * 5
        } else if (level != 5 && level != 11 && level != 17 && level != 23) {
            breakTime = (curVal * 5) - (curVal * (((level - (level / 6)) % 5) - 1))
            breakSecond = 0
            breakMinute = (curVal * 5) - (curVal * (((level - (level / 6)) % 5) - 1))
        } else {
            breakTime = curVal
            breakSecond = 0
            breakMinute = curVal
        }



        totaltimenum.text = String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
        if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23)
            breaktimenum.text = String.format(" ")
        else
            breaktimenum.text =
                    String.format("%02d : %02d : %02d", breakHour, breakMinute, breakSecond)

        breakMinute--
        if (((level - (level / 6)) % 5 == 0) && level != 5 && level != 11 && level != 17 && level != 23) {
            text1.setText("BREAK TIME")
            levelText.setText("BREAK")
            levelVal.setText("TIME")
        } else {
            text1.setText("TIME FOR NEXT BLIND")
            levelText.setText("Level")
            levelVal.text = String.format("%d", level - (level / 6))
        }
        levelval(level)
        anteval(level)
        reset()
    }

    private fun leveldownfor7() {
        totalTime = (level - 2) * 60 * curVal
        if (level == 1)
            totalMinute = (level - 1) * curVal
        else
            totalMinute = (level - 2) * curVal
        totalHour = totalMinute / 60
        totalMinute = totalMinute % 60
        totalSecond = 0

        if (level == 1)
            level++
        level--
        totaltimenum.text = String.format("%02d : %02d : %02d", totalHour, totalMinute, totalSecond)
        breaktimenum.text = String.format(" ")
        text1.setText("TIME FOR NEXT BLIND")
        levelText.setText("Level")
        levelVal.text = String.format("%d", level)
        levelvalfor7(level)
        antevalfor7(level)
        reset()
    }

    private fun levelval(number: Int) {
        if (number == 1) {
            BlindVal.text = String.format("1000/2000")
            NextBlindVal.text = String.format("2000/4000")
        } else if (number == 2) {
            BlindVal.text = String.format("2000/4000")
            NextBlindVal.text = String.format("3000/6000")
        } else if (number == 3) {
            BlindVal.text = String.format("3000/6000")
            NextBlindVal.text = String.format("4000/8000")
        } else if (number == 4) {
            BlindVal.text = String.format("4000/8000")
            NextBlindVal.text = String.format("5000/1만")
        } else if (number == 5) {
            BlindVal.text = String.format("5000/1만")
            NextBlindVal.text = String.format("1만/2만")
        } else if (number == 6) {
            BlindVal.text = String.format(" ")
            NextBlindVal.text = String.format("1만/2만")
        } else if (number == 7)//6
        {
            BlindVal.text = String.format("1만/2만")
            NextBlindVal.text = String.format("2만/4만")
        } else if (number == 8) {
            BlindVal.text = String.format("2만/4만")
            NextBlindVal.text = String.format("3만/6만")
        } else if (number == 9) {
            BlindVal.text = String.format("3만/6만")
            NextBlindVal.text = String.format("4만/8만")
        } else if (number == 10) {
            BlindVal.text = String.format("4만/8만")
            NextBlindVal.text = String.format("5만/10만")
        } else if (number == 11)//10
        {
            BlindVal.text = String.format("5만/10만")
            NextBlindVal.text = String.format("10만/20만")
        } else if (number == 12) {
            BlindVal.text = String.format(" ")
            NextBlindVal.text = String.format("10만/20만")
        } else if (number == 13) {
            BlindVal.text = String.format("10만/20만")
            NextBlindVal.text = String.format("20만/40만")
        } else if (number == 14) {
            BlindVal.text = String.format("20만/40만")
            NextBlindVal.text = String.format("30만/60만")
        } else if (number == 15) {
            BlindVal.text = String.format("30만/60만")
            NextBlindVal.text = String.format("40만/80만")
        } else if (number == 16) {
            BlindVal.text = String.format("40만/80만")
            NextBlindVal.text = String.format("50만/100만")
        } else if (number == 17)//15
        {
            BlindVal.text = String.format("50만/100만")
            NextBlindVal.text = String.format("100만/200만")
        } else if (number == 18) {
            BlindVal.text = String.format(" ")
            NextBlindVal.text = String.format("100만/200만")
        } else if (number == 19) {
            BlindVal.text = String.format("100만/200만")
            NextBlindVal.text = String.format("200만/400만")
        } else if (number == 20) {
            BlindVal.text = String.format("200만/400만")
            NextBlindVal.text = String.format("300만/600만")
        } else if (number == 21) {
            BlindVal.text = String.format("300만/600만")
            NextBlindVal.text = String.format("400만/800만")
        } else if (number == 22) {
            BlindVal.text = String.format("400만/800만")
            NextBlindVal.text = String.format("500만/1000만")
        } else if (number == 23) {
            BlindVal.text = String.format("500만/1000만")
            NextBlindVal.text = String.format(" ")
        }


    }

    private fun levelvalfor7(number: Int) {
        if (number == 1) {
            BlindVal.text = String.format("1000/2000")
            NextBlindVal.text = String.format("2000/4000")
        } else if (number == 2) {
            BlindVal.text = String.format("2000/4000")
            NextBlindVal.text = String.format("3000/6000")
        } else if (number == 3) {
            BlindVal.text = String.format("3000/6000")
            NextBlindVal.text = String.format("4000/8000")
        } else if (number == 4) {
            BlindVal.text = String.format("4000/8000")
            NextBlindVal.text = String.format("5000/1만")
        } else if (number == 5) {
            BlindVal.text = String.format("5000/1만")
            NextBlindVal.text = String.format("1만/2만")
        } else if (number == 6)//6
        {
            BlindVal.text = String.format("1만/2만")
            NextBlindVal.text = String.format("2만/4만")
        } else if (number == 7) {
            BlindVal.text = String.format("2만/4만")
            NextBlindVal.text = String.format("3만/6만")
        } else if (number == 8) {
            BlindVal.text = String.format("3만/6만")
            NextBlindVal.text = String.format("4만/8만")
        } else if (number == 9) {
            BlindVal.text = String.format("4만/8만")
            NextBlindVal.text = String.format("5만/10만")
        } else if (number == 10)//10
        {
            BlindVal.text = String.format("5만/10만")
            NextBlindVal.text = String.format("10만/20만")
        } else if (number == 11) {
            BlindVal.text = String.format("10만/20만")
            NextBlindVal.text = String.format("20만/40만")
        } else if (number == 12) {
            BlindVal.text = String.format("20만/40만")
            NextBlindVal.text = String.format("30만/60만")
        } else if (number == 13) {
            BlindVal.text = String.format("30만/60만")
            NextBlindVal.text = String.format("40만/80만")
        } else if (number == 14) {
            BlindVal.text = String.format("40만/80만")
            NextBlindVal.text = String.format("50만/100만")
        } else if (number == 15)//15
        {
            BlindVal.text = String.format("50만/100만")
            NextBlindVal.text = String.format("100만/200만")
        } else if (number == 16) {
            BlindVal.text = String.format("100만/200만")
            NextBlindVal.text = String.format("200만/400만")
        } else if (number == 17) {
            BlindVal.text = String.format("200만/400만")
            NextBlindVal.text = String.format("300만/600만")
        } else if (number == 18) {
            BlindVal.text = String.format("300만/600만")
            NextBlindVal.text = String.format("400만/800만")
        } else if (number == 19) {
            BlindVal.text = String.format("400만/800만")
            NextBlindVal.text = String.format("500만/1000만")
        } else if (number == 20) {
            BlindVal.text = String.format("500만/1000만")
            NextBlindVal.text = String.format(" ")
        }


    }

    private fun anteval(number: Int) {
        if (number == 1) {
            AnteVal.text = String.format(" ")
            NextAnteVal.text = String.format(" ")
        } else if (number == 2) {
            AnteVal.text = String.format(" ")
            NextAnteVal.text = String.format("6000")
        } else if (number == 3) {
            AnteVal.text = String.format("6000")
            NextAnteVal.text = String.format("8000")
        } else if (number == 4) {
            AnteVal.text = String.format("8000")
            NextAnteVal.text = String.format("1만")
        } else if (number == 5) {
            AnteVal.text = String.format("1만")
            NextAnteVal.text = String.format("2만")
        } else if (number == 6) {
            AnteVal.text = String.format(" ")
            NextAnteVal.text = String.format("2만")
        } else if (number == 7) {
            AnteVal.text = String.format("2만")
            NextAnteVal.text = String.format("4만")
        } else if (number == 8) {
            AnteVal.text = String.format("4만")
            NextAnteVal.text = String.format("6만")
        } else if (number == 9) {
            AnteVal.text = String.format("6만")
            NextAnteVal.text = String.format("8만")
        } else if (number == 10) {
            AnteVal.text = String.format("8만")
            NextAnteVal.text = String.format("10만")
        } else if (number == 11) {
            AnteVal.text = String.format("10만")
            NextAnteVal.text = String.format("20만")
        } else if (number == 12) {
            AnteVal.text = String.format(" ")
            NextAnteVal.text = String.format("20만")
        } else if (number == 13) {
            AnteVal.text = String.format("20만")
            NextAnteVal.text = String.format("40만")
        } else if (number == 14) {
            AnteVal.text = String.format("40만")
            NextAnteVal.text = String.format("60만")
        } else if (number == 15) {
            AnteVal.text = String.format("60만")
            NextAnteVal.text = String.format("80만")
        } else if (number == 16) {
            AnteVal.text = String.format("80만")
            NextAnteVal.text = String.format("100만")
        } else if (number == 17) {
            AnteVal.text = String.format("100만")
            NextAnteVal.text = String.format("200만")
        } else if (number == 18) {
            AnteVal.text = String.format(" ")
            NextAnteVal.text = String.format("200만")
        } else if (number == 19) {
            AnteVal.text = String.format("200만")
            NextAnteVal.text = String.format("400만")
        } else if (number == 20) {
            AnteVal.text = String.format("400만")
            NextAnteVal.text = String.format("600만")
        } else if (number == 21) {
            AnteVal.text = String.format("600만")
            NextAnteVal.text = String.format("800만")
        } else if (number == 22) {
            AnteVal.text = String.format("800만")
            NextAnteVal.text = String.format("1000만")
        } else if (number == 23) {
            AnteVal.text = String.format("1000만")
            NextAnteVal.text = String.format("-")
        } else {
            AnteVal.text = String.format(" ")
            NextAnteVal.text = String.format(" ")
        }


    }

    private fun antevalfor7(number: Int) {

        AnteVal.text = String.format(" ")
        NextAnteVal.text = String.format(" ")

    }
}

