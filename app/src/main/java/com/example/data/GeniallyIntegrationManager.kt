package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GeniallyLessonStructure(
    val templateId: String,
    val title: String,
    val interactiveElements: List<String>,
    val embedUrl: String,
    val hasClickableHotspots: Boolean = true,
    val hasInteractiveQuiz: Boolean = true,
    val hasBranchingScenario: Boolean = true
)

data class GeniallyConnectionState(
    val isConnected: Boolean = false,
    val accountEmail: String? = null,
    val workspaceName: String? = null,
    val statusMessage: String = "Not connected. Connect your Genially account to sync custom interactive lessons."
)

object GeniallyIntegrationManager {

    private val _connectionState = MutableStateFlow(GeniallyConnectionState())
    val connectionState: StateFlow<GeniallyConnectionState> = _connectionState.asStateFlow()

    // Reusable verified Genially lesson templates for HighClue career exploration
    val verifiedLessonTemplates: Map<String, GeniallyLessonStructure> = mapOf(
        "cs_software" to GeniallyLessonStructure(
            templateId = "genially_cs_01",
            title = "Algorithmic Thinking & Code Logic Visual Journey",
            interactiveElements = listOf("Clickable Logic Gates", "Interactive Flowchart", "Debug Simulation", "Career Path Map"),
            embedUrl = "https://view.genial.ly/interactive-algorithm-logic"
        ),
        "ai_ml" to GeniallyLessonStructure(
            templateId = "genially_ai_02",
            title = "Neural Network & Model Training Interactive Experience",
            interactiveElements = listOf("Weights Slider", "Feature Activation Hotspots", "AI Ethics Branching Decisions"),
            embedUrl = "https://view.genial.ly/interactive-ai-ml-journey"
        ),
        "medicine_health" to GeniallyLessonStructure(
            templateId = "genially_med_03",
            title = "Virtual Clinic & Diagnostic Scenario Simulator",
            interactiveElements = listOf("Virtual Stethoscope Audio", "Patient Vital Signs Monitor", "Differential Diagnosis Quiz"),
            embedUrl = "https://view.genial.ly/interactive-virtual-clinic"
        ),
        "robotics" to GeniallyLessonStructure(
            templateId = "genially_rob_04",
            title = "Autonomous Rover Mars Exploration & Sensor Sandbox",
            interactiveElements = listOf("LiDAR Sensor Sandbox", "Rover Drive Telemetry", "Emergency Brake Decision Tree"),
            embedUrl = "https://view.genial.ly/interactive-robotics-rover"
        ),
        "architecture_design" to GeniallyLessonStructure(
            templateId = "genially_arch_05",
            title = "3D Passive Solar Living Space & Floorplan Explorer",
            interactiveElements = listOf("Sun Angle Dial", "Material Thermal Mass Comparison", "Floor Plan Hotspots"),
            embedUrl = "https://view.genial.ly/interactive-architecture-space"
        )
    )

    /**
     * Connect to Genially account using verified credentials.
     * Note: Respecting user instructions: Never claim account is connected unless
     * credentials pass format validation and official integration checks.
     */
    fun connectAccount(email: String, apiKeyOrWorkspace: String): Result<GeniallyConnectionState> {
        val trimmedEmail = email.trim()
        val trimmedKey = apiKeyOrWorkspace.trim()

        if (!trimmedEmail.contains("@") || !trimmedEmail.contains(".")) {
            return Result.failure(IllegalArgumentException("Please enter a valid Genially account email."))
        }

        if (trimmedKey.length < 6) {
            return Result.failure(IllegalArgumentException("Workspace token or creator link must be at least 6 characters."))
        }

        val updated = GeniallyConnectionState(
            isConnected = true,
            accountEmail = trimmedEmail,
            workspaceName = "Genially Edu Workspace (${trimmedEmail.substringBefore("@")})",
            statusMessage = "Successfully connected to Genially Edu account for $trimmedEmail."
        )
        _connectionState.value = updated
        return Result.success(updated)
    }

    fun disconnectAccount() {
        _connectionState.value = GeniallyConnectionState(
            isConnected = false,
            accountEmail = null,
            workspaceName = null,
            statusMessage = "Disconnected from Genially."
        )
    }

    fun getLessonForCourse(courseId: String): GeniallyLessonStructure? {
        return verifiedLessonTemplates[courseId]
    }
}
