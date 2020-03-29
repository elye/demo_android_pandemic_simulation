package com.elyeproj.surfaceviewexplore

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.seek_infected
import kotlinx.android.synthetic.main.activity_main.seek_infectious_distance
import kotlinx.android.synthetic.main.activity_main.seek_mobility_distance
import kotlinx.android.synthetic.main.activity_main.seek_uninfected
import kotlinx.android.synthetic.main.activity_main.simulate_view_button
import kotlinx.android.synthetic.main.activity_main.title_infected
import kotlinx.android.synthetic.main.activity_main.title_infectious_distance
import kotlinx.android.synthetic.main.activity_main.title_mobility_distance
import kotlinx.android.synthetic.main.activity_main.title_uninfected

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title_infected.text = getString(R.string.title_infected_people,
            resources.getInteger(R.integer.default_infected_people))
        title_uninfected.text = getString(R.string.title_uninfected_people,
            resources.getInteger(R.integer.default_uninfected_people))
        title_mobility_distance.text = getString(R.string.title_mobility_distance,
            resources.getInteger(R.integer.default_mobility_distance))
        title_infectious_distance.text = getString(R.string.title_infectious_distance,
            resources.getInteger(R.integer.default_infectious_distance))

        seek_infected.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                title_infected.text = getString(R.string.title_infected_people, progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seek_uninfected.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                title_uninfected.text = getString(R.string.title_uninfected_people, progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seek_mobility_distance.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                title_mobility_distance.text = getString(R.string.title_mobility_distance, progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seek_infectious_distance.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                title_infectious_distance.text = getString(R.string.title_infectious_distance, progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        simulate_view_button.setOnClickListener {
            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra(SimulationActivity.KEY_INFECTED, seek_infected.progress)
            intent.putExtra(SimulationActivity.KEY_UNINFECTED, seek_uninfected.progress)
            intent.putExtra(SimulationActivity.KEY_MOBILITY, seek_mobility_distance.progress)
            intent.putExtra(SimulationActivity.KEY_INFECTIOUS, seek_infectious_distance.progress)
            startActivity(intent)
        }
    }
}
