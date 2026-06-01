package com.scoutapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scoutapp.databinding.ItemAccionTecnicaBinding
import com.scoutapp.model.AccionTecnica

class AccionTecnicaAdapter(
    private val acciones: List<AccionTecnica>
) : RecyclerView.Adapter<AccionTecnicaAdapter.AccionTecnicaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccionTecnicaViewHolder {
        val binding = ItemAccionTecnicaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AccionTecnicaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccionTecnicaViewHolder, position: Int) {
        holder.bind(acciones[position])
    }

    override fun getItemCount(): Int = acciones.size

    fun getAcciones(): List<AccionTecnica> = acciones

    fun limpiarValoraciones() {
        acciones.forEach { it.valoracion = 0 }
        notifyDataSetChanged()
    }

    inner class AccionTecnicaViewHolder(
        private val binding: ItemAccionTecnicaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(accion: AccionTecnica) {
            binding.apply {
                tvNombreAccion.text = accion.nombre
                tvCategoria.text = accion.categoria
                tvDescripcion.text = accion.descripcion

                // Configurar el SeekBar
                sbValoracion.max = 10
                sbValoracion.progress = accion.valoracion
                tvValoracion.text = accion.valoracion.toString()

                sbValoracion.setOnSeekBarChangeListener(
                    object : android.widget.SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: android.widget.SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            accion.valoracion = progress
                            tvValoracion.text = progress.toString()
                        }

                        override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}

                        override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
                    }
                )

                // EditText para observaciones
                etObservacionesAccion.setText(accion.observaciones)
                etObservacionesAccion.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        accion.observaciones = etObservacionesAccion.text.toString()
                    }
                }
            }
        }
    }
}
