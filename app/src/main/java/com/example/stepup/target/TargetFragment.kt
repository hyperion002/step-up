package com.example.stepup.target

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.activityViewModels
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.stepup.R

class TargetFragment : PreferenceFragmentCompat() {

    private val viewModel: TargetViewModel by activityViewModels { TargetViewModel }
//    private var _binding: FragmentTargetBinding? = null
//    private val binding get() = _binding!!
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentTargetBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeTargetChanges()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.target, rootKey)

        val dailyGoalPreference = preferenceManager.findPreference<EditTextPreference>("daily_goal")
        dailyGoalPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> {
            val dailyGoal = it.text?.toIntOrNull() ?:0
            resources.getQuantityString(R.plurals.daily_goal_summary, dailyGoal, dailyGoal)
        }

        val numericPreferenceKey = listOf("daily_goal", "step_length", "height", "weight")
        numericPreferenceKey.forEach {
            val preference = preferenceManager.findPreference<EditTextPreference>(it)
            preference?.setOnBindEditTextListener { editText ->
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
    }
}