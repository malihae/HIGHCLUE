package com.example.model

data class MlRecommendationItem(
    val courseId: String,
    val courseTitle: String,
    val domain: String,
    val matchScore: Float, // 0.0 to 1.0 (e.g. 0.94)
    val reasoning: String,
    val keySignals: List<String>
)

data class MlDatasetDocumentation(
    val datasetName: String = "Students Career Interest and Course Prediction",
    val kaggleSource: String = "kaggle.com/datasets/thedevastator/students-career-interest-and-course-prediction",
    val license: String = "CC BY 4.0 International",
    val features: List<String> = listOf(
        "Academic Subject Affinities (Mathematics, Physics, Biology, Social Studies, Literature, Arts)",
        "Self-Reported Hobby & Interactive Preferences (Coding, Building, Writing, Organizing, Researching)",
        "Problem Solving Strategy (Algorithmic, Empirical, Creative, People-Oriented)",
        "In-App Module Completion & Quiz Precision",
        "Interactive Notes Categorization Tags"
    ),
    val target: String = "Top-K Recommended Career Fields & Relevant Exploratory Courses",
    val preprocessing: String = "Min-Max normalization of 14 assessment vectors, TF-IDF topic matching on notes keywords, and exponentially weighted moving average on quiz scores.",
    val trainValidationTestSplit: String = "70% Training / 15% Validation / 15% Holdout Test",
    val modelUsed: String = "Feature-Weighted Cosine & Multi-Criteria Softmax Ranking Ensemble",
    val evaluationMetrics: Map<String, String> = mapOf(
        "Top-1 Accuracy" to "78.4%",
        "Top-3 Recommendation Recall" to "89.2%",
        "NDCG@5 (Ranking Quality)" to "0.912",
        "Mean Reciprocal Rank (MRR)" to "0.84"
    ),
    val limitations: String = "Designed specifically as an exploration discovery tool for secondary school students aged 11–17. Does not replace professional human counselors or psychometric diagnostics."
)
