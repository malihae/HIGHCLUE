package com.example.model

data class KnowledgeChunk(
    val id: String,
    val domain: String,
    val topic: String,
    val content: String,
    val sourceTitle: String,
    val sourceOrg: String,
    val sourceUrl: String,
    val keywords: List<String>
)

data class RagMessage(
    val id: String,
    val sender: String, // "user", "assistant", "system"
    val text: String,
    val citedSources: List<KnowledgeChunk> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

data class RagQueryResponse(
    val answer: String,
    val citedSources: List<KnowledgeChunk>,
    val confidence: Float,
    val groundingDisclaimer: String = "Grounded in verified academic subject guides and youth career pathways."
)
