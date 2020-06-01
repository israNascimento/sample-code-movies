package io.petros.movies.core.fragment

import androidx.lifecycle.Observer
import dev.fanie.stateful.StatefulInstance
import io.petros.movies.core.view_model.MviViewModel
import timber.log.Timber

abstract class MviFragment<
        INTENT : Any,
        STATE : Any,
        STATEFUL_STATE : StatefulInstance<STATE>,
        SIDE_EFFECT : Any,
        VIEW_MODEL : MviViewModel<INTENT, STATE, SIDE_EFFECT>
        >(layout: Int) : BaseFragment(layout) {

    abstract val viewModel: VIEW_MODEL
    abstract val stateful: STATEFUL_STATE

    private val state = Observer<STATE> {
        Timber.v("${javaClass.simpleName} observed state. [State: $it]")
        stateful.accept(it)
    }

    private val sideEffect = Observer<SIDE_EFFECT> {
        Timber.v("${javaClass.simpleName} observed side effect. [Side Effect: $it]")
        renderSideEffect(it)
    }

    override fun onResume() {
        super.onResume()
        stateful.clear()
        initObservers()
    }

    private fun initObservers() {
        viewModel.state().observe(this, state)
        viewModel.sideEffect().observe(this, sideEffect)
    }

    abstract fun renderSideEffect(sideEffect: SIDE_EFFECT): Unit?

}
