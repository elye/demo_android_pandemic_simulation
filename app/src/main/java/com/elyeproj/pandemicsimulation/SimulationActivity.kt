package com.elyeproj.pandemicsimulation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SimulationActivity : AppCompatActivity() {

    companion object {
        const val KEY_INFECTED = "InfectedKey"
        const val KEY_UNINFECTED = "UninfectedKey"
        const val KEY_MOBILITY = "MobilityKey"
        const val KEY_INFECTIOUS = "InfectiousKEy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SimulationView.globalInfected = intent.getIntExtra(KEY_INFECTED, 0)
        SimulationView.globalUninfected = intent.getIntExtra(KEY_UNINFECTED, 0)
        SimulationView.globalMobilityDistance = intent.getIntExtra(KEY_MOBILITY, 0)
        SimulationView.globalInfectiousDistance = intent.getIntExtra(KEY_INFECTIOUS, 0)
        setContentView(R.layout.activity_simulation)
    }
}
