package com.elyeproj.surfaceviewexplore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.seek_infected
import kotlinx.android.synthetic.main.activity_main.seek_mobility_distance
import kotlinx.android.synthetic.main.activity_main.seek_uninfected
import kotlinx.android.synthetic.main.activity_main.simulate_view_button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simulate_view_button.setOnClickListener {
            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra(SimulationActivity.KEY_INFECTED, seek_infected.progress)
            intent.putExtra(SimulationActivity.KEY_UNINFECTED, seek_uninfected.progress)
            intent.putExtra(SimulationActivity.KEY_MOBILITY, seek_mobility_distance.progress)
            startActivity(intent)
        }
    }
}
