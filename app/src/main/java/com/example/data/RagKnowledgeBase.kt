package com.example.data

import com.example.BuildConfig
import com.example.model.KnowledgeChunk
import com.example.model.RagQueryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object RagKnowledgeBase {

    val knowledgeChunks: List<KnowledgeChunk> = listOf(
        KnowledgeChunk(
            id = "rag_cs_pathways",
            domain = "Computer Science & Software",
            topic = "High School Subjects & University Degrees",
            content = "For Computer Science and Software Engineering, high school students should focus on Advanced Mathematics (Pre-Calculus/Calculus), Informatics/Computer Science (Python or Java), and Physics. At university, typical degrees include B.S. in Computer Science, Software Engineering, and Computer Systems. Software engineers write application logic, manage cloud databases, and optimize algorithms.",
            sourceTitle = "ACM/IEEE Computing Curricula & BLS Occupational Handbook",
            sourceOrg = "Association for Computing Machinery (ACM)",
            sourceUrl = "https://www.acm.org/education/curricula-recommendations",
            keywords = listOf("computer", "software", "coding", "math", "calculus", "degree", "jobs", "developer", "engineer")
        ),
        KnowledgeChunk(
            id = "rag_ai_ml_guide",
            domain = "Artificial Intelligence & Data",
            topic = "AI Specialization, Ethics & Pathways",
            content = "Artificial Intelligence and Machine Learning careers require strong foundations in Linear Algebra, Calculus, Statistics, and Python. Typical degrees include Computer Science (AI concentration), Data Science, or Cognitive Science. Roles include ML Engineer, AI Research Scientist, and Data Strategist. Entry-level students can start with Kaggle micro-courses and beginner Python data analysis.",
            sourceTitle = "Stanford AI Index Report & Career Pathways Guide",
            sourceOrg = "Stanford Institute for Human-Centered AI (HAI)",
            sourceUrl = "https://hai.stanford.edu/ai-index-report",
            keywords = listOf("ai", "machine learning", "neural", "python", "statistics", "data", "deep learning", "ethics")
        ),
        KnowledgeChunk(
            id = "rag_medicine_pathways",
            domain = "Medicine & Healthcare",
            topic = "Pre-Med Requirements & Clinical Roles",
            content = "Entering Healthcare and Medicine requires strong high school preparation in Chemistry, Biology, and Physics. In university, students typically major in Biology, Biochemistry, or Human Health (Pre-Med track), followed by Medical School (MD/DO) or Nursing (BSN). Careers range from Pediatricians, Surgeons, and Neurologists to Physician Assistants and Biomedical Researchers.",
            sourceTitle = "AAMC Medical School Admission Requirements (MSAR)",
            sourceOrg = "Association of American Medical Colleges (AAMC)",
            sourceUrl = "https://www.aamc.org/students/aspiring-doctors",
            keywords = listOf("doctor", "medicine", "health", "hospital", "biology", "chemistry", "pre-med", "surgeon", "nurse")
        ),
        KnowledgeChunk(
            id = "rag_robotics_engineering",
            domain = "Robotics & Automation",
            topic = "Mechatronics, Hardware & Robotics Pathways",
            content = "Robotics bridges Mechanical Engineering, Electrical Engineering, and Computer Science. High school students benefit from Physics, Trigonometry, and hands-on robotics clubs (such as FIRST Robotics). University degrees include B.S. in Robotics Engineering or Mechatronics. Daily responsibilities involve testing sensors, programming microcontrollers, and designing autonomous control loops.",
            sourceTitle = "IEEE Robotics and Automation Society Career Overview",
            sourceOrg = "IEEE RAS",
            sourceUrl = "https://www.ieee-ras.org/education",
            keywords = listOf("robot", "robotics", "automation", "mechatronics", "hardware", "circuits", "sensors", "mechanical")
        ),
        KnowledgeChunk(
            id = "rag_cybersecurity_def",
            domain = "Cybersecurity",
            topic = "Information Security Pathways & Certifications",
            content = "Cybersecurity professionals defend critical digital infrastructure. Essential subjects include Computer Networking, Discrete Mathematics, and Operating Systems. Degrees in Cybersecurity or Computer Science paired with foundational hands-on CTF (Capture the Flag) competitions are ideal. Roles include Security Analyst, Penetration Tester (Ethical Hacker), and Digital Forensics Investigator.",
            sourceTitle = "NIST NICE Cybersecurity Workforce Framework",
            sourceOrg = "National Institute of Standards and Technology (NICE)",
            sourceUrl = "https://www.nist.gov/itl/applied-cybersecurity/nice",
            keywords = listOf("cybersecurity", "security", "hacking", "ethical", "defense", "forensics", "network", "crypto")
        ),
        KnowledgeChunk(
            id = "rag_architecture_design",
            domain = "Architecture & Design",
            topic = "B.Arch Degree, Studio Portfolios & Spatial Planning",
            content = "Becoming a licensed architect requires a NAAB-accredited Bachelor of Architecture (5-year B.Arch) or a Master of Architecture. High schoolers should cultivate Visual Arts (sketching, portfolio), Geometry, and Environmental Science. Architects balance aesthetics, building codes, passive solar heating, and sustainable materials.",
            sourceTitle = "National Council of Architectural Registration Boards (NCARB)",
            sourceOrg = "NCARB Guidelines",
            sourceUrl = "https://www.ncarb.org/become-architect",
            keywords = listOf("architecture", "architect", "building", "design", "drawing", "spatial", "urban", "construction")
        ),
        KnowledgeChunk(
            id = "rag_business_entrepreneurship",
            domain = "Business & Entrepreneurship",
            topic = "Starting Startups, Venture Management & Pitching",
            content = "Business and entrepreneurship focus on identifying market gaps, validating customer needs, managing finances, and building products. Helpful high school subjects include Economics, Speech/Debate, and Mathematics. Degrees include B.B.A. (Business Administration), Management, and Finance. Startup founders must master communication, resource allocation, and resilience.",
            sourceTitle = "Global Entrepreneurship Monitor (GEM) Youth Education Report",
            sourceOrg = "Babson College & GEM Consortium",
            sourceUrl = "https://www.gemconsortium.org",
            keywords = listOf("business", "startup", "entrepreneur", "founder", "money", "management", "pitch", "product")
        ),
        KnowledgeChunk(
            id = "rag_law_social_justice",
            domain = "Law & Social Sciences",
            topic = "Pre-Law Prep, Bar Exam & Public Policy",
            content = "Practicing law requires graduating from college and attending a 3-year Law School (J.D.) before passing the Bar exam. Excellent pre-law majors include Political Science, Philosophy, History, and English. Skills emphasized are analytical reading, persuasive writing, legal ethics, and oral argumentation.",
            sourceTitle = "American Bar Association (ABA) Pre-Law Preparation Guide",
            sourceOrg = "American Bar Association",
            sourceUrl = "https://www.americanbar.org/groups/legal_education",
            keywords = listOf("law", "lawyer", "attorney", "justice", "court", "judge", "policy", "history", "debate")
        ),
        KnowledgeChunk(
            id = "rag_psychology_cognition",
            domain = "Psychology & Cognitive Science",
            topic = "Clinical Psychology, Neuroscience & Counseling",
            content = "Psychology investigates human perception, emotion, and behavior. High school students should take Biology, Psychology, and Statistics. A Bachelor's degree (B.A. or B.S. in Psychology) opens pathways to research, human resources, and user experience (UX) research; becoming a licensed clinical psychologist requires a Psy.D. or Ph.D.",
            sourceTitle = "American Psychological Association (APA) Student Guide",
            sourceOrg = "American Psychological Association",
            sourceUrl = "https://www.apa.org/education-career",
            keywords = listOf("psychology", "brain", "mental", "counseling", "behavior", "cognitive", "therapy", "empathy")
        ),
        KnowledgeChunk(
            id = "rag_environmental_solutions",
            domain = "Environmental Science",
            topic = "Climate Solutions, Renewable Energy & Ecology",
            content = "Environmental scientists evaluate ecosystems, monitor carbon cycles, and design renewable energy transitions. Key high school subjects are Earth Science, Chemistry, and Biology. Degrees include Environmental Science, Conservation Ecology, and Renewable Energy Engineering. Careers include Climate Analyst, Park Ranger, and Corporate Sustainability Officer.",
            sourceTitle = "United Nations Environment Programme (UNEP) Youth & Careers",
            sourceOrg = "UN Environment Programme",
            sourceUrl = "https://www.unep.org/youth",
            keywords = listOf("environment", "climate", "sustainability", "renewable", "earth", "nature", "green", "carbon")
        ),
        KnowledgeChunk(
            id = "rag_biotechnology_gene",
            domain = "Biotechnology & Life Sciences",
            topic = "CRISPR, Genetics & Pharmaceutical Innovation",
            content = "Biotechnology uses biological mechanisms like DNA and enzymes to invent medicines, biofuels, and disease-resistant crops. High school students should excel in Biology (Genetics) and Chemistry. University degrees include Biotechnology, Molecular Genetics, and Bioengineering. Roles include Geneticist, Bioinformatician, and Pharmaceutical Research Associate.",
            sourceTitle = "Biotechnology Innovation Organization (BIO) Education Guide",
            sourceOrg = "Biotechnology Innovation Organization",
            sourceUrl = "https://www.bio.org/policy/human-health",
            keywords = listOf("biotech", "genetics", "dna", "crispr", "biology", "cells", "medicine", "vaccine", "bio")
        )
    )

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    /**
     * Retrieve top-K relevant knowledge chunks using weighted keyword similarity.
     */
    fun retrieveRelevantChunks(query: String, topK: Int = 3): List<KnowledgeChunk> {
        val tokens = query.lowercase().split(Regex("[^a-z0-9]+")).filter { it.length > 2 }
        if (tokens.isEmpty()) return knowledgeChunks.take(topK)

        val scored = knowledgeChunks.map { chunk ->
            var score = 0
            val text = (chunk.topic + " " + chunk.content + " " + chunk.keywords.joinToString(" ")).lowercase()
            for (token in tokens) {
                if (chunk.keywords.contains(token)) score += 5
                if (chunk.topic.lowercase().contains(token)) score += 3
                if (text.contains(token)) score += 1
            }
            Pair(chunk, score)
        }

        val sorted = scored.sortedByDescending { it.second }
        val results = sorted.take(topK).map { it.first }
        return if (results.isNotEmpty()) results else knowledgeChunks.take(topK)
    }

    /**
     * Grounded Question Answering pipeline:
     * 1. Retrieve trusted knowledge chunks
     * 2. If Gemini API key is available, prompt Gemini with grounded constraint
     * 3. Otherwise, assemble a grounded synthesis from the verified chunks
     */
    suspend fun askCareerAssistant(userQuestion: String): RagQueryResponse = withContext(Dispatchers.IO) {
        val retrieved = retrieveRelevantChunks(userQuestion, topK = 3)
        val apiKey = BuildConfig.GEMINI_API_KEY

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val answer = callGeminiRestApi(userQuestion, retrieved, apiKey)
                return@withContext RagQueryResponse(
                    answer = answer,
                    citedSources = retrieved,
                    confidence = 0.95f
                )
            } catch (e: Exception) {
                // Graceful fallback to grounded knowledge base synthesizer
            }
        }

        // Local Grounded Synthesizer
        val localAnswer = synthesizeGroundedResponse(userQuestion, retrieved)
        RagQueryResponse(
            answer = localAnswer,
            citedSources = retrieved,
            confidence = 0.88f
        )
    }

    private fun callGeminiRestApi(
        question: String,
        chunks: List<KnowledgeChunk>,
        apiKey: String
    ): String {
        val contextText = chunks.joinToString("\n\n") {
            "Domain: ${it.domain}\nTopic: ${it.topic}\nContent: ${it.content}\nSource: ${it.sourceTitle} (${it.sourceOrg})"
        }

        val prompt = """
            You are HighClue AI Career Guide, an encouraging, grounded advisor for youth aged 11–17.
            Answer the student's question based strictly on the following verified career knowledge context.
            Do not invent unrealistic requirements. Keep it friendly, clear, and inspiring.
            
            VERIFIED CONTEXT:
            $contextText
            
            STUDENT QUESTION:
            $question
            
            Provide a helpful 2-4 paragraph explanation with next steps for high school students.
        """.trimIndent()

        val jsonBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", prompt))
                    })
                })
            })
        }

        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = httpClient.newCall(request).execute()
        val responseBody = response.body?.string() ?: ""
        if (!response.isSuccessful) {
            throw IllegalStateException("API error: ${response.code}")
        }

        val respJson = JSONObject(responseBody)
        val text = respJson.getJSONArray("candidates")
            .getJSONObject(0)
            .getJSONObject("content")
            .getJSONArray("parts")
            .getJSONObject(0)
            .getString("text")

        return text.trim()
    }

    private fun synthesizeGroundedResponse(
        question: String,
        chunks: List<KnowledgeChunk>
    ): String {
        val primary = chunks.firstOrNull() ?: return "HighClue has verified learning pathways across 15 career domains. Try asking about Computer Science, Medicine, Robotics, Engineering, or Law!"
        
        return buildString {
            append("Great question! When exploring ")
            append(primary.domain)
            append(", here is what academic and industry pathways recommend:\n\n")
            append("• Key Subject Foundations & Pathways:\n")
            append(primary.content)
            append("\n\n")
            if (chunks.size > 1) {
                append("• Related Exploratory Domain (")
                append(chunks[1].domain)
                append("):\n")
                append(chunks[1].content)
                append("\n\n")
            }
            append("• Recommended Next Steps for Ages 11–17:\n")
            append("1. Explore introductory modules in HighClue to test your hands-on enjoyment.\n")
            append("2. Focus on strong foundational school coursework rather than hyper-specializing early.\n")
            append("3. Discuss elective choices with your school counselor or career advisor.")
        }
    }
}
