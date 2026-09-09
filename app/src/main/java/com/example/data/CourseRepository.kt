package com.example.data

import com.example.model.Course
import com.example.model.CourseModule
import com.example.model.MiniQuizQuestion
import com.example.model.VisualActivity

object CourseRepository {

    val courses: List<Course> by lazy {
        listOf(
            createCourse1(),
            createCourse2(),
            createCourse3(),
            createCourse4(),
            createCourse5(),
            createCourse6(),
            createCourse7(),
            createCourse8(),
            createCourse9(),
            createCourse10(),
            createCourse11(),
            createCourse12(),
            createCourse13(),
            createCourse14(),
            createCourse15()
        )
    }

    fun getCourseById(id: String): Course? = courses.find { it.id == id }

    fun searchCourses(query: String, selectedDomain: String? = null): List<Course> {
        return courses.filter { course ->
            val matchesDomain = selectedDomain == null || selectedDomain == "All" || course.domain.equals(selectedDomain, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                    course.title.contains(query, ignoreCase = true) ||
                    course.shortDescription.contains(query, ignoreCase = true) ||
                    course.skills.any { it.contains(query, ignoreCase = true) } ||
                    course.category.contains(query, ignoreCase = true)
            matchesDomain && matchesQuery
        }
    }

    // 1. Computer Science & Software Engineering
    private fun createCourse1() = Course(
        id = "cs_software",
        title = "Computer Science & Software Engineering",
        domain = "Technology",
        category = "Coding & Systems",
        shortDescription = "Learn how apps, games, and operating systems are designed from logic and algorithms.",
        fullDescription = "Software engineering isn't just about typing code into a dark screen; it's about solving real-world puzzles for billions of people. In this course, you'll discover how computers think, how modular software is built, and what a day in the life of a software engineer looks like.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2.5 hrs",
        skills = listOf("Algorithmic Thinking", "Code Architecture", "Debugging", "Data Structures"),
        iconName = "Code",
        colorHex = "#4F46E5",
        universityMajors = listOf("B.S. Computer Science", "Software Engineering", "Computer Systems"),
        highSchoolSubjects = listOf("Computer Science / Informatics", "Mathematics", "Physics"),
        geniallyTemplateUrl = "https://view.genial.ly/65e90001example/interactive-cs-intro",
        modules = listOf(
            CourseModule(
                id = "cs_m1",
                courseId = "cs_software",
                title = "How Computers Think: Algorithms & Logic",
                orderIndex = 1,
                learningObjective = "Understand what an algorithm is and how computers execute sequential logic.",
                explanation = "Imagine baking a cake without a recipe—you might end up with salty batter! An algorithm is simply a step-by-step recipe that a computer executes without guessing. Even complex 3D games and video streaming apps run on billions of tiny, simple instructions.",
                visualActivity = VisualActivity(
                    title = "Sorting Algorithm Visualizer",
                    description = "Order an unsorted deck of cards using the Bubble Sort and Quick Sort logic.",
                    interactiveType = "STEPPER",
                    stepsOrScenarios = listOf(
                        "Step 1: Compare adjacent card numbers (e.g., 7 and 3).",
                        "Step 2: If the left card is larger, swap their positions.",
                        "Step 3: Repeat until no more swaps are needed throughout the row."
                    ),
                    takeaway = "Sorting efficiently reduces the time computer processors spend searching for data."
                ),
                realWorldExample = "How Spotify instantly sorts your 2,000-song playlist by artist name in milliseconds using QuickSort.",
                careerConnection = "Software Engineers and Systems Architects write and optimize these algorithms daily.",
                skillDeveloped = "Logical Decomposition",
                estimatedMinutes = 12,
                quiz = MiniQuizQuestion(
                    id = "cs_q1",
                    question = "What is the primary definition of an algorithm in computing?",
                    options = listOf(
                        "A physical chip inside the monitor",
                        "A step-by-step set of clear instructions to solve a problem",
                        "A computer virus that slows down memory",
                        "A graphic design tool for web layouts"
                    ),
                    correctIndex = 1,
                    explanation = "An algorithm is an unambiguous sequence of steps designed to perform a specific calculation or solve a problem."
                ),
                geniallyUrl = "https://view.genial.ly/interactive-algorithm-logic"
            ),
            CourseModule(
                id = "cs_m2",
                courseId = "cs_software",
                title = "Debugging Like a Detective",
                orderIndex = 2,
                learningObjective = "Learn how programmers locate and resolve unexpected software bugs.",
                explanation = "In 1947, engineers found an actual moth trapped inside the Harvard Mark II computer—giving birth to the term 'bug'! Today, software bugs are logic mistakes made by humans. Debugging is the science of testing assumptions and tracing errors step-by-step.",
                visualActivity = VisualActivity(
                    title = "Spot the Glitch Challenge",
                    description = "Inspect a simple shopping cart code snippet where the total price increases when a discount coupon is applied.",
                    interactiveType = "CHOICE_SIMULATION",
                    stepsOrScenarios = listOf(
                        "Code Line 12: total = subtotal + discount",
                        "Expected: total = subtotal - discount",
                        "Fix: Switch addition operator '+' to subtraction operator '-'."
                    ),
                    takeaway = "One single incorrect character can alter the behavior of an entire banking or space mission program."
                ),
                realWorldExample = "NASA's Mariner 1 rocket in 1962 went off course due to a missing hyphen in the flight guidance software.",
                careerConnection = "Quality Assurance (QA) Engineers and Site Reliability Engineers specialize in finding bugs before launch.",
                skillDeveloped = "Analytical Debugging",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "cs_q2",
                    question = "Why is writing automated tests helpful for software teams?",
                    options = listOf(
                        "It makes the computer screen brighter",
                        "It catches bugs automatically whenever new features are added",
                        "It deletes unused code permanently",
                        "It prevents students from playing computer games"
                    ),
                    correctIndex = 1,
                    explanation = "Automated tests act as a safety net, verifying that new updates don't accidentally break existing features."
                )
            ),
            CourseModule(
                id = "cs_m3",
                courseId = "cs_software",
                title = "Building User Experiences: Front-End vs Back-End",
                orderIndex = 3,
                learningObjective = "Differentiate between client-side interfaces and server-side databases.",
                explanation = "Think of an app like a restaurant: the dining room where you sit and look at menus is the Front-End (UI). The kitchen where chefs cook and the pantry stores food is the Back-End (Server & Database). Great software requires harmony between both.",
                visualActivity = VisualActivity(
                    title = "App Architecture Blueprint",
                    description = "Trace a button click from screen to cloud server and back.",
                    interactiveType = "EXPLORER",
                    stepsOrScenarios = listOf(
                        "1. User taps 'Like' in Compose UI.",
                        "2. Network sends an HTTP POST request to API server.",
                        "3. Server updates counter in database and confirms '200 OK'."
                    ),
                    takeaway = "Front-end focuses on design and ergonomics, back-end focuses on security, speed, and data integrity."
                ),
                realWorldExample = "Instagram's smooth interface runs on your phone, but billions of photos are hosted on distributed cloud servers.",
                careerConnection = "Full-Stack Developers bridge both client interfaces and cloud backends.",
                skillDeveloped = "Systems Architecture",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "cs_q3",
                    question = "Where is user data like passwords and account history safely stored?",
                    options = listOf(
                        "Inside the user's phone camera",
                        "On secure back-end cloud databases",
                        "Inside the app launcher icon",
                        "On the user's home television"
                    ),
                    correctIndex = 1,
                    explanation = "Sensitive records are encrypted and maintained in secure back-end databases, protected by authentication."
                )
            )
        )
    )

    // 2. Artificial Intelligence & Machine Learning
    private fun createCourse2() = Course(
        id = "ai_ml",
        title = "Artificial Intelligence & Machine Learning",
        domain = "Technology",
        category = "Data & Intelligence",
        shortDescription = "Discover how neural networks learn from patterns rather than explicit rules.",
        fullDescription = "How does your phone recognize your face, translate languages in real time, or generate creative stories? Machine learning teaches computers to spot patterns in massive datasets. Explore training, neural networks, ethics, and youth opportunities.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2.5 hrs",
        skills = listOf("Pattern Recognition", "Model Training", "Prompt Engineering", "AI Ethics"),
        iconName = "SmartToy",
        colorHex = "#0EA5E9",
        universityMajors = listOf("Artificial Intelligence", "Cognitive Science", "Applied Mathematics"),
        highSchoolSubjects = listOf("Calculus / Statistics", "Computer Science", "Philosophy / Ethics"),
        geniallyTemplateUrl = "https://view.genial.ly/interactive-ai-ml-journey",
        modules = listOf(
            CourseModule(
                id = "ai_m1",
                courseId = "ai_ml",
                title = "Rule-Based Code vs Machine Learning",
                orderIndex = 1,
                learningObjective = "Understand the fundamental difference between hardcoded rules and learned models.",
                explanation = "Traditional programming requires giving explicit instructions for every case: 'If email contains FREE, mark spam'. But spammers change words quickly! Machine learning instead feeds thousands of spam and clean emails into a model so it discovers the distinguishing patterns by itself.",
                visualActivity = VisualActivity(
                    title = "Cat vs Dog Classifier",
                    description = "See how feature extractors examine whiskers, ear shapes, and fur texture.",
                    interactiveType = "LOGIC_PUZZLE",
                    stepsOrScenarios = listOf(
                        "Feature 1: Ear pointiness ratio.",
                        "Feature 2: Snout length vs head width.",
                        "Result: Probability 94% Cat, 6% Dog."
                    ),
                    takeaway = "Machine learning models make statistical predictions based on trained weights, not absolute certainties."
                ),
                realWorldExample = "Google Photos grouping your pictures by pet or vacation location without you manually tagging every photo.",
                careerConnection = "Machine Learning Engineers train and deploy these pattern recognition models.",
                skillDeveloped = "Probabilistic Thinking",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "ai_q1",
                    question = "How does a machine learning model improve its predictions?",
                    options = listOf(
                        "By turning the computer off and on again",
                        "By adjusting internal weights through training on feedback data",
                        "By printing more paper copies of the code",
                        "By buying a larger keyboard"
                    ),
                    correctIndex = 1,
                    explanation = "During training, loss functions calculate error and algorithms (like backpropagation) adjust weights to reduce future mistakes."
                )
            ),
            CourseModule(
                id = "ai_m2",
                courseId = "ai_ml",
                title = "AI Ethics: Fairness, Bias, and Safety",
                orderIndex = 2,
                learningObjective = "Evaluate the social impacts and ethical dilemmas surrounding artificial intelligence.",
                explanation = "AI learns from historical human data. If the historical data contains biases or lacks diversity, the AI will learn and amplify those exact biases! Understanding AI safety ensures technology helps all humans fairly.",
                visualActivity = VisualActivity(
                    title = "The Autonomous Car Dilemma",
                    description = "Simulate how safety engineers program collision avoidance parameters under unexpected sensor failures.",
                    interactiveType = "CHOICE_SIMULATION",
                    stepsOrScenarios = listOf(
                        "Scenario: An unexpected obstacle blocks both lanes in heavy fog.",
                        "Option A: Prioritize stopping distance at all costs.",
                        "Option B: Steer toward an empty road shoulder."
                    ),
                    takeaway = "Engineers must bake human values and safety constraints directly into system architectures."
                ),
                realWorldExample = "Facial recognition systems struggling with darker skin tones because training datasets lacked diverse faces.",
                careerConnection = "AI Ethics Researchers and Policy Advisors help guide governments and tech companies.",
                skillDeveloped = "Critical Ethical Reasoning",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "ai_q2",
                    question = "Why can an AI model become biased?",
                    options = listOf(
                        "Computers have feelings like jealousy",
                        "The dataset it learned from was unrepresentative or biased",
                        "The internet ran out of bandwidth",
                        "The model was coded in lowercase letters"
                    ),
                    correctIndex = 1,
                    explanation = "An AI model is a reflection of its training data; biased or incomplete inputs produce biased predictions."
                )
            )
        )
    )

    // 3. Data Science & Analytics
    private fun createCourse3() = Course(
        id = "data_science",
        title = "Data Science & Analytics",
        domain = "Technology",
        category = "Data & Insights",
        shortDescription = "Turn messy numbers and statistics into compelling stories and critical decisions.",
        fullDescription = "Data is the new compass of the world. Sports teams use data to win championships, epidemiologists use it to curb outbreaks, and climate scientists use it to forecast severe storms. Master data storytelling, dashboards, and predictive modeling.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Data Storytelling", "Statistical Analysis", "Data Visualization", "Hypothesis Testing"),
        iconName = "BarChart",
        colorHex = "#10B981",
        universityMajors = listOf("Data Science", "Applied Statistics", "Business Analytics"),
        highSchoolSubjects = listOf("Statistics / Math", "Economics", "Informatics"),
        modules = listOf(
            CourseModule(
                id = "ds_m1",
                courseId = "data_science",
                title = "From Chaos to Charts: Data Cleaning",
                orderIndex = 1,
                learningObjective = "Learn why 80% of data science is cleaning messy real-world datasets.",
                explanation = "Real data is messy: missing values, typos, duplicate records, and crazy outliers! Data scientists clean and prepare data so decisions aren't based on corrupted numbers.",
                visualActivity = VisualActivity(
                    title = "Clean the School Cafeteria Dataset",
                    description = "Identify three common errors in survey responses: negative numbers, typo entries, and duplicate submissions.",
                    interactiveType = "EXPLORER",
                    stepsOrScenarios = listOf(
                        "Entry 1: Age = -14 (Error: Negative age).",
                        "Entry 2: Favorite Fruit = 'Appple' (Typo).",
                        "Entry 3: Student ID #4092 repeated twice with identical answers."
                    ),
                    takeaway = "Garbage in equals garbage out. Clean data ensures trustworthy results."
                ),
                realWorldExample = "The movie 'Moneyball'—how the Oakland Athletics baseball team used statistics to beat rich teams.",
                careerConnection = "Data Analysts, Business Intelligence Specialists, and Quantitative Researchers.",
                skillDeveloped = "Data Hygiene & Scrutiny",
                estimatedMinutes = 12,
                quiz = MiniQuizQuestion(
                    id = "ds_q1",
                    question = "What does the phrase 'Correlation does not imply causation' mean?",
                    options = listOf(
                        "Graphs should always be drawn in green ink",
                        "Just because two things happen together doesn't mean one caused the other",
                        "Calculators never make mistakes",
                        "Data science is only used for video games"
                    ),
                    correctIndex = 1,
                    explanation = "Ice cream sales and shark attacks both rise in summer, but ice cream does not cause shark attacks—warm weather causes people to swim and eat ice cream!"
                )
            )
        )
    )

    // 4. Cybersecurity
    private fun createCourse4() = Course(
        id = "cybersecurity",
        title = "Cybersecurity",
        domain = "Technology",
        category = "Defense & Cryptography",
        shortDescription = "Learn ethical hacking, digital forensics, and how to defend hospitals and power grids.",
        fullDescription = "Every second, thousands of automated bots scan servers for weaknesses. Cybersecurity experts are digital guardians who protect schools, hospitals, banking apps, and space networks from cyberattacks.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Ethical Hacking", "Cryptography", "Network Defense", "Threat Modeling"),
        iconName = "Security",
        colorHex = "#6366F1",
        universityMajors = listOf("Cybersecurity Engineering", "Information Assurance", "Computer Networks"),
        highSchoolSubjects = listOf("Computer Science", "Discrete Math", "Law & Ethics"),
        modules = listOf(
            CourseModule(
                id = "cy_m1",
                courseId = "cybersecurity",
                title = "Secrets & Ciphers: The Power of Encryption",
                orderIndex = 1,
                learningObjective = "Explore how public key cryptography scrambles messages so only the intended recipient can read them.",
                explanation = "Julius Caesar shifted alphabet letters by 3 to send secret war orders. Today, end-to-end encryption uses massive prime numbers to lock your chat messages so even internet service providers cannot peek inside.",
                visualActivity = VisualActivity(
                    title = "The Caesar Cipher Decoder Ring",
                    description = "Shift the alphabet by +3 to encode the word 'EXPLORE' into 'HASORUH'.",
                    interactiveType = "LOGIC_PUZZLE",
                    stepsOrScenarios = listOf(
                        "Original letter: E -> +3 -> H",
                        "Original letter: X -> +3 -> A (wraps around)",
                        "Original letter: P -> +3 -> S"
                    ),
                    takeaway = "Modern AES-256 encryption is so strong that even a supercomputer would take billions of years to guess the key."
                ),
                realWorldExample = "How WhatsApp and Signal secure private phone calls with end-to-end encryption.",
                careerConnection = "Penetration Testers ('White Hat Hackers') and Security Analysts.",
                skillDeveloped = "Cryptographic Awareness",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "cy_q1",
                    question = "What is a 'White Hat' ethical hacker?",
                    options = listOf(
                        "Someone who hacks into games to get free coins",
                        "A security professional authorized to find security flaws so they can be fixed",
                        "A hacker who operates only during snowy winter months",
                        "A robot that replaces computers"
                    ),
                    correctIndex = 1,
                    explanation = "Ethical hackers use their skills for defense, uncovering vulnerabilities with permission to make systems safer."
                )
            )
        )
    )

    // 5. Robotics & Automation
    private fun createCourse5() = Course(
        id = "robotics",
        title = "Robotics & Automation",
        domain = "Engineering",
        category = "Hardware & Mechatronics",
        shortDescription = "Blend mechanical arms, smart sensors, and microcontrollers to automate the physical world.",
        fullDescription = "Robotics combines mechanical hardware, electrical circuits, and intelligent software code. From surgical robots performing delicate eye operations to rovers exploring Martian craters, robots expand human boundaries.",
        difficulty = "Intermediate",
        estimatedTime = "2.5 hrs",
        skills = listOf("Mechatronics", "Sensor Integration", "Feedback Control Loops", "Kinematics"),
        iconName = "PrecisionManufacturing",
        colorHex = "#F59E0B",
        universityMajors = listOf("Robotics Engineering", "Mechatronics", "Mechanical Engineering"),
        highSchoolSubjects = listOf("Physics (Mechanics)", "Trigonometry", "Electronics / Shop"),
        modules = listOf(
            CourseModule(
                id = "rob_m1",
                courseId = "robotics",
                title = "Sensors, Actuators & The Sense-Plan-Act Loop",
                orderIndex = 1,
                learningObjective = "Understand how autonomous robots perceive their environment and move safely.",
                explanation = "A robot needs three things: Eyes & Ears (Sensors like LiDAR and ultrasonic distance finders), a Brain (Microcontroller running logic), and Muscles (Actuators, servos, and DC motors). This is the 'Sense-Plan-Act' loop.",
                visualActivity = VisualActivity(
                    title = "Mars Rover Obstacle Avoidance",
                    description = "Program a rover's sensor threshold to brake when an obstacle is detected within 30cm.",
                    interactiveType = "CHOICE_SIMULATION",
                    stepsOrScenarios = listOf(
                        "Distance reading: 85cm -> Action: Drive forward at 50% power.",
                        "Distance reading: 22cm -> Action: Reverse 10cm, rotate 45 degrees right.",
                        "Clear path verified -> Action: Resume journey."
                    ),
                    takeaway = "Real-time control loops run hundreds of times per second to prevent crashes."
                ),
                realWorldExample = "NASA's Perseverance Rover autonomously navigating rocky Martian terrain without joystick lag from Earth.",
                careerConnection = "Robotics Engineers, Automation Specialists, and Firmware Developers.",
                skillDeveloped = "Control Loop Logic",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "rob_q1",
                    question = "In robotics, what is an actuator?",
                    options = listOf(
                        "The battery charging cable",
                        "The component that converts electrical energy into physical motion (like a motor)",
                        "The screen displaying system time",
                        "A software compiler"
                    ),
                    correctIndex = 1,
                    explanation = "Actuators are the 'muscles' of a robot, turning electrical signals into physical movement."
                )
            )
        )
    )

    // 6. Medicine & Healthcare
    private fun createCourse6() = Course(
        id = "medicine_health",
        title = "Medicine & Healthcare",
        domain = "Health",
        category = "Clinical & Biomedical",
        shortDescription = "Investigate human anatomy, disease diagnosis, surgical innovations, and public wellness.",
        fullDescription = "Doctors, surgeons, nurses, and medical researchers collaborate to heal illness and save lives. Learn how clinicians think like scientific detectives, how diagnostics reveal invisible conditions, and how global public health works.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2.5 hrs",
        skills = listOf("Diagnostic Thinking", "Human Anatomy", "Patient Empathy", "Biomedical Ethics"),
        iconName = "LocalHospital",
        colorHex = "#EF4444",
        universityMajors = listOf("Pre-Medicine / Biology", "Nursing", "Biomedical Sciences"),
        highSchoolSubjects = listOf("Biology", "Chemistry", "Psychology / Health"),
        modules = listOf(
            CourseModule(
                id = "med_m1",
                courseId = "medicine_health",
                title = "The Medical Detective: Clinical Diagnosis",
                orderIndex = 1,
                learningObjective = "Experience how doctors listen to symptoms, order tests, and deduce the root cause.",
                explanation = "When a patient visits a clinic, a physician conducts a differential diagnosis. They take vital signs (heart rate, blood pressure, oxygen saturation, temperature), listen to family history, and eliminate unlikely causes.",
                visualActivity = VisualActivity(
                    title = "Virtual ER Case File",
                    description = "Review a patient with acute shortness of breath and wheezing after playing soccer in high pollen count.",
                    interactiveType = "CHOICE_SIMULATION",
                    stepsOrScenarios = listOf(
                        "Vitals: Heart rate 110 bpm, Oxygen Sat 93% (low), Peak expiratory flow reduced.",
                        "History: Prior mild childhood eczema, family history of allergies.",
                        "Hypothesis: Acute asthma exacerbation triggered by aeroallergens."
                    ),
                    takeaway = "Doctors never guess; they systematically correlate symptoms with biological mechanisms."
                ),
                realWorldExample = "How emergency rooms use triage systems to treat the most life-threatening emergencies first.",
                careerConnection = "Physicians, Pediatricians, Surgeons, Nurse Practitioners, and Paramedics.",
                skillDeveloped = "Diagnostic Reasoning",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "med_q1",
                    question = "What does a stethoscope allow doctors to hear?",
                    options = listOf(
                        "Bone density readings",
                        "Heart valve sounds, breathing airflow, and intestinal sounds",
                        "Brain wave frequencies",
                        "Blood type group"
                    ),
                    correctIndex = 1,
                    explanation = "Stethoscopes amplify internal acoustic sounds like the 'lub-dub' rhythm of heart valves opening and closing."
                )
            )
        )
    )

    // 7. Engineering
    private fun createCourse7() = Course(
        id = "engineering",
        title = "Engineering (Civil, Electrical, Mechanical)",
        domain = "Engineering",
        category = "Physical Systems",
        shortDescription = "Design skyscrapers that withstand earthquakes, renewable energy grids, and jet engines.",
        fullDescription = "Engineers apply physics and mathematical principles to build the physical foundations of civilization: suspension bridges, electric vehicles, satellite arrays, water purification plants, and sustainable cities.",
        difficulty = "Intermediate",
        estimatedTime = "2.5 hrs",
        skills = listOf("Structural Mechanics", "Thermodynamics", "CAD Design", "Problem Optimization"),
        iconName = "Build",
        colorHex = "#D97706",
        universityMajors = listOf("Civil Engineering", "Mechanical Engineering", "Electrical Engineering"),
        highSchoolSubjects = listOf("Physics", "Calculus", "Chemistry"),
        modules = listOf(
            CourseModule(
                id = "eng_m1",
                courseId = "engineering",
                title = "Tension & Compression: How Bridges Stand",
                orderIndex = 1,
                learningObjective = "Discover the forces of tension, compression, and shear that hold structures upright.",
                explanation = "Push down on a spring, and it compresses; pull on a rope, and it tenses! Bridges and towers balance these forces so materials never buckle under heavy weight or high winds.",
                visualActivity = VisualActivity(
                    title = "Truss Bridge Load Simulator",
                    description = "Distribute heavy truck weight across triangular truss members to minimize stress concentrations.",
                    interactiveType = "LOGIC_PUZZLE",
                    stepsOrScenarios = listOf(
                        "Top chords undergo COMPRESSION (squeezed together).",
                        "Bottom chords undergo TENSION (pulled apart).",
                        "Triangles remain rigid because their angles cannot deform without changing side lengths."
                    ),
                    takeaway = "Triangles are the strongest geometric shapes in structural engineering."
                ),
                realWorldExample = "The Golden Gate Bridge in San Francisco holding up cars through massive steel suspension cables in tension.",
                careerConnection = "Structural Engineers, Civil Planners, and Bridge Inspectors.",
                skillDeveloped = "Force Balance Analysis",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "eng_q1",
                    question = "Which force pulls and stretches a structural material apart?",
                    options = listOf(
                        "Compression",
                        "Tension",
                        "Gravity inversion",
                        "Magnetic friction"
                    ),
                    correctIndex = 1,
                    explanation = "Tension pulls and stretches materials, while compression pushes and squashes them together."
                )
            )
        )
    )

    // 8. Architecture & Design
    private fun createCourse8() = Course(
        id = "architecture_design",
        title = "Architecture & Design",
        domain = "Creative",
        category = "Spatial & Visual",
        shortDescription = "Blend art, spatial psychology, and sustainable materials to create inspiring living spaces.",
        fullDescription = "Architecture is inhabited art. Good architects ask: How do sunlight, natural ventilation, and human psychology influence how we feel inside a school, hospital, or museum? Explore floor plans, 3D modeling, and green building design.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Spatial Thinking", "Design Aesthetics", "Sustainable Building", "Human-Centered Design"),
        iconName = "DesignServices",
        colorHex = "#EC4899",
        universityMajors = listOf("Architecture (B.Arch)", "Urban Planning", "Interior Architecture"),
        highSchoolSubjects = listOf("Visual Arts / Drawing", "Geometry", "Environmental Studies"),
        modules = listOf(
            CourseModule(
                id = "arch_m1",
                courseId = "architecture_design",
                title = "Passive Solar Design & Natural Lighting",
                orderIndex = 1,
                learningObjective = "Understand how window orientation and shading keep buildings warm in winter and cool in summer.",
                explanation = "Before air conditioning was invented, ancient builders positioned homes to catch summer breezes and maximize winter sun. Today, sustainable architects use biophilic design to create carbon-neutral buildings.",
                visualActivity = VisualActivity(
                    title = "Sun Path Explorer",
                    description = "Position overhangs (eaves) above south-facing windows to block high summer sun while letting low winter sun penetrate.",
                    interactiveType = "EXPLORER",
                    stepsOrScenarios = listOf(
                        "Summer: Sun is high at 70° elevation -> Overhang casts complete shade.",
                        "Winter: Sun is low at 25° elevation -> Sunlight streams deep into living rooms.",
                        "Result: Saves up to 40% on heating and cooling electricity bills."
                    ),
                    takeaway = "Smart spatial geometry can heat and cool a building naturally without consuming fossil fuels."
                ),
                realWorldExample = "The Sydney Opera House with its shell-shaped roof inspired by peeling orange segments.",
                careerConnection = "Licensed Architects, Urban Designers, and Sustainable Energy Consultants.",
                skillDeveloped = "Spatial Optimization",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "arch_q1",
                    question = "Why do architects orient large windows toward the winter sun in cold climates?",
                    options = listOf(
                        "To watch birds fly south",
                        "To capture natural passive thermal heat and daylight, reducing energy use",
                        "Because glass is cheaper than brick",
                        "To prevent indoor plants from growing"
                    ),
                    correctIndex = 1,
                    explanation = "Passive solar design harnesses the sun's natural energy to illuminate and warm spaces efficiently."
                )
            )
        )
    )

    // 9. Business & Entrepreneurship
    private fun createCourse9() = Course(
        id = "business_ent",
        title = "Business & Entrepreneurship",
        domain = "Business",
        category = "Strategy & Innovation",
        shortDescription = "Turn innovative ideas into viable startups that solve customer pain points profitably.",
        fullDescription = "How do startups go from a dorm room idea to a global enterprise? Entrepreneurs identify unsolved problems, build minimum viable products (MVPs), pitch to investors, and build passionate teams.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Entrepreneurial Mindset", "Market Validation", "Team Leadership", "Pitching"),
        iconName = "BusinessCenter",
        colorHex = "#8B5CF6",
        universityMajors = listOf("Business Administration", "Entrepreneurship", "Management Science"),
        highSchoolSubjects = listOf("Business Studies", "Economics", "Communications / Speech"),
        modules = listOf(
            CourseModule(
                id = "biz_m1",
                courseId = "business_ent",
                title = "The Lean Startup & Minimum Viable Product (MVP)",
                orderIndex = 1,
                learningObjective = "Learn how to validate an idea quickly with minimal budget before building a full product.",
                explanation = "Many failed businesses spend years building something nobody wanted! The Lean Startup methodology tests hypotheses with an MVP—the simplest version of your product that allows you to collect validated feedback from real customers.",
                visualActivity = VisualActivity(
                    title = "The Food Truck MVP Test",
                    description = "Before opening a million-dollar restaurant, test your recipe with a weekend pop-up stall.",
                    interactiveType = "CHOICE_SIMULATION",
                    stepsOrScenarios = listOf(
                        "Step 1: Offer 3 signature taco flavors to 50 students.",
                        "Step 2: Collect feedback on taste, price ($5 vs $8), and speed.",
                        "Step 3: Pivot the menu based on real customer votes before signing a lease."
                    ),
                    takeaway = "Fail fast and learn cheaply so you can build what people truly love."
                ),
                realWorldExample = "Dropbox founder Drew Houston validated demand by posting a simple 3-minute demo video before writing complex server sync code.",
                careerConnection = "Startup Founders, Product Managers, and Venture Capital Analysts.",
                skillDeveloped = "Agile Hypothesis Testing",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "biz_q1",
                    question = "What is a Minimum Viable Product (MVP)?",
                    options = listOf(
                        "The most expensive item in a luxury store",
                        "The simplest version of a product created to test assumptions with real users",
                        "A contract between banks and governments",
                        "A completed software update that took 10 years to build"
                    ),
                    correctIndex = 1,
                    explanation = "An MVP enables teams to learn rapidly from early adopters with the least amount of wasted effort."
                )
            )
        )
    )

    // 10. Finance & Economics
    private fun createCourse10() = Course(
        id = "finance_econ",
        title = "Finance & Economics",
        domain = "Business",
        category = "Markets & Capital",
        shortDescription = "Master compound interest, supply & demand, global trade, and investment portfolios.",
        fullDescription = "Money is the language of trade and resources. Economics explores how societies allocate scarce resources, while finance teaches how individuals and corporations invest, manage risk, and build long-term wealth through compound growth.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Financial Literacy", "Market Analysis", "Risk Assessment", "Macroeconomics"),
        iconName = "MonetizationOn",
        colorHex = "#059669",
        universityMajors = listOf("Finance", "Economics (B.A. / B.S.)", "Actuarial Science"),
        highSchoolSubjects = listOf("Economics", "Accounting", "Applied Mathematics"),
        modules = listOf(
            CourseModule(
                id = "fin_m1",
                courseId = "finance_econ",
                title = "The Eighth Wonder: Compound Interest & Investing",
                orderIndex = 1,
                learningObjective = "Understand how exponential interest multiplies savings over decades.",
                explanation = "Simple interest pays you only on your original deposit. Compound interest pays interest on your interest! Starting to invest small amounts at age 15 creates vastly greater wealth than starting at age 35, due to the power of compounding time.",
                visualActivity = VisualActivity(
                    title = "Compound Growth Calculator",
                    description = "Compare $100 saved monthly at 8% annual return over 10 vs 30 years.",
                    interactiveType = "EXPLORER",
                    stepsOrScenarios = listOf(
                        "10 Years ($12k deposited): Balance grows to ~$18,294.",
                        "20 Years ($24k deposited): Balance grows to ~$58,902.",
                        "30 Years ($36k deposited): Balance skyrockets to ~$149,035!"
                    ),
                    takeaway = "Time in the market is more powerful than timing the market."
                ),
                realWorldExample = "Warren Buffett earned over 99% of his total wealth after his 50th birthday due to long-term compounding.",
                careerConnection = "Investment Bankers, Wealth Advisors, Financial Analysts, and Economists.",
                skillDeveloped = "Long-Term Financial Planning",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "fin_q1",
                    question = "What makes compound interest grow faster than simple interest?",
                    options = listOf(
                        "Banks give you secret lottery tickets",
                        "You earn interest on both your initial deposit and previously accumulated interest",
                        "The government prints new cash every hour",
                        "It only works on leap years"
                    ),
                    correctIndex = 1,
                    explanation = "Compounding earns returns on prior earnings, creating an exponential growth curve over time."
                )
            )
        )
    )

    // 11. Law & Social Sciences
    private fun createCourse11() = Course(
        id = "law_social",
        title = "Law & Social Sciences",
        domain = "Social Sciences",
        category = "Justice & Policy",
        shortDescription = "Examine justice, constitutional rights, persuasive advocacy, and global diplomacy.",
        fullDescription = "How do laws protect human freedoms and resolve conflicts peacefully? Explore the judicial system, international law, mock trials, diplomacy, and how social policy shapes modern communities.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2.5 hrs",
        skills = listOf("Persuasive Advocacy", "Constitutional Analysis", "Legal Research", "Conflict Mediation"),
        iconName = "Gavel",
        colorHex = "#7C3AED",
        universityMajors = listOf("Pre-Law / Juris Doctor", "Political Science", "Sociology", "International Relations"),
        highSchoolSubjects = listOf("History / Civics", "Debate / Speech", "Literature / English"),
        modules = listOf(
            CourseModule(
                id = "law_m1",
                courseId = "law_social",
                title = "The Anatomy of a Mock Trial",
                orderIndex = 1,
                learningObjective = "Differentiate between burden of proof in criminal vs civil law and construct a sound argument.",
                explanation = "In court, justice relies on evidence and constitutional rules. In criminal cases, prosecutors must prove guilt 'beyond a reasonable doubt'. Defense attorneys protect rights by cross-examining witnesses and challenging unconstitutional searches.",
                visualActivity = VisualActivity(
                    title = "Objection! In the Courtroom",
                    description = "Identify hearsay, leading questions on direct examination, and speculative witness claims.",
                    interactiveType = "CHOICE_SIMULATION",
                    stepsOrScenarios = listOf(
                        "Statement: 'My neighbor told me he heard the defendant broke the lock.'",
                        "Proper Objection: Objection, Hearsay! (Out-of-court statement offered for the truth of the matter).",
                        "Judge Ruling: Sustained. The jury must disregard."
                    ),
                    takeaway = "Rules of evidence guarantee that trials depend on verifiable facts, not rumors."
                ),
                realWorldExample = "The landmark Brown v. Board of Education Supreme Court case establishing that segregated schools violate equality.",
                careerConnection = "Trial Lawyers, Appellate Judges, Human Rights Advocates, and Policy Advisors.",
                skillDeveloped = "Critical Cross-Examination",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "law_q1",
                    question = "What is the standard of proof required in a criminal trial?",
                    options = listOf(
                        "A coin toss",
                        "Beyond a reasonable doubt",
                        "A guess by the mayor",
                        "Whoever speaks loudest wins"
                    ),
                    correctIndex = 1,
                    explanation = "Because a person's liberty is at stake, criminal convictions require certainty beyond reasonable doubt."
                )
            )
        )
    )

    // 12. Psychology
    private fun createCourse12() = Course(
        id = "psychology",
        title = "Psychology & Cognitive Science",
        domain = "Social Sciences",
        category = "Human Behavior",
        shortDescription = "Uncover how the human brain perceives memories, manages stress, and forms social bonds.",
        fullDescription = "Why do we procrastinate? How do optical illusions trick our eyes? Psychology explores the conscious and subconscious mind, human development, mental health treatments, and cognitive biases.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Cognitive Empathy", "Behavioral Analysis", "Experimental Design", "Mental Health Literacy"),
        iconName = "Psychology",
        colorHex = "#E11D48",
        universityMajors = listOf("Psychology (B.A. / B.S.)", "Cognitive Neuroscience", "Counseling"),
        highSchoolSubjects = listOf("Psychology", "Biology", "Sociology"),
        modules = listOf(
            CourseModule(
                id = "psy_m1",
                courseId = "psychology",
                title = "Cognitive Biases: How Our Brains Take Shortcuts",
                orderIndex = 1,
                learningObjective = "Identify confirmation bias and heuristics that influence human decision-making.",
                explanation = "Our brains process thousands of decisions daily by using mental shortcuts called heuristics. However, these shortcuts often cause cognitive biases, such as favoring evidence that confirms our existing beliefs while ignoring contradictory facts.",
                visualActivity = VisualActivity(
                    title = "Spot Confirmation Bias",
                    description = "Notice how reading only comments that agree with your opinion strengthens false assumptions.",
                    interactiveType = "LOGIC_PUZZLE",
                    stepsOrScenarios = listOf(
                        "Test: Search 'Why my favorite game is the best ever'.",
                        "Search Engine displays glowing reviews, ignoring bug complaints.",
                        "Fix: Actively search for counter-arguments to form a balanced, objective perspective."
                    ),
                    takeaway = "Self-awareness of cognitive biases helps students make wiser, more empathetic choices."
                ),
                realWorldExample = "How social media algorithms feed echo chambers by serving posts you already agree with.",
                careerConnection = "Clinical Psychologists, Neuropsychologists, School Counselors, and UX Researchers.",
                skillDeveloped = "Meta-Cognitive Reflection",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "psy_q1",
                    question = "What is Confirmation Bias?",
                    options = listOf(
                        "A technical malfunction in video cameras",
                        "The human tendency to search for and remember information that confirms prior beliefs",
                        "A method for memorizing multiplication tables",
                        "The ability to speak two languages fluently"
                    ),
                    correctIndex = 1,
                    explanation = "Confirmation bias leads people to seek out information that supports their opinions while ignoring opposing facts."
                )
            )
        )
    )

    // 13. Media, Film & Content Creation
    private fun createCourse13() = Course(
        id = "media_creation",
        title = "Media, Film & Content Creation",
        domain = "Creative",
        category = "Storytelling & Digital Production",
        shortDescription = "Learn cinematography, audio design, narrative arcs, and digital journalism.",
        fullDescription = "Stories move the world. From feature films and documentary podcasts to viral YouTube essays, content creators shape culture. Explore camera framing, non-linear editing, sound design, and ethical digital journalism.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Visual Storytelling", "Cinematography", "Audio Mixing", "Digital Editing"),
        iconName = "Movie",
        colorHex = "#F43F5E",
        universityMajors = listOf("Film & Television Production", "Journalism", "Digital Media"),
        highSchoolSubjects = listOf("Media Studies", "Creative Writing", "Drama / Photography"),
        modules = listOf(
            CourseModule(
                id = "medc_m1",
                courseId = "media_creation",
                title = "The Rule of Thirds & Cinematic Framing",
                orderIndex = 1,
                learningObjective = "Master camera angles and composition to evoke emotional resonance.",
                explanation = "Placing a subject dead-center can feel flat or static. The Rule of Thirds divides your viewfinder into a 3x3 grid, placing points of interest at the intersections to create dynamic tension and cinematic flow.",
                visualActivity = VisualActivity(
                    title = "Director's Viewfinder Simulator",
                    description = "Frame a character's eyes along the upper horizontal third line to convey intimacy and emotion.",
                    interactiveType = "EXPLORER",
                    stepsOrScenarios = listOf(
                        "Low Angle: Looking UP at character makes them appear powerful or intimidating.",
                        "High Angle: Looking DOWN makes them appear vulnerable or lost.",
                        "Dutch Angle (tilted): Conveys disorientation and psychological unease."
                    ),
                    takeaway = "Camera angles tell the audience how to feel before the actors even speak a line of dialogue."
                ),
                realWorldExample = "Director Steven Spielberg using wide horizon compositions in Jurassic Park to emphasize the majestic scale of dinosaurs.",
                careerConnection = "Cinematographers, Film Directors, Video Editors, and Creative Producers.",
                skillDeveloped = "Visual Composition",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "medc_q1",
                    question = "What effect does a 'low camera angle' typically achieve in cinematography?",
                    options = listOf(
                        "Makes the actor look tiny and weak",
                        "Makes the subject look authoritative, imposing, or heroic",
                        "Turns the screen black and white",
                        "Speeds up the video playback"
                    ),
                    correctIndex = 1,
                    explanation = "Looking up from a low angle emphasizes height and presence, making subjects appear dominant or heroic."
                )
            )
        )
    )

    // 14. Environmental Science & Sustainability
    private fun createCourse14() = Course(
        id = "environmental_sci",
        title = "Environmental Science & Sustainability",
        domain = "Environment",
        category = "Ecology & Climate Solutions",
        shortDescription = "Investigate renewable energy, circular economies, biodiversity, and climate resilience.",
        fullDescription = "Our planet faces urgent ecological challenges. Environmental scientists measure atmospheric carbon, conserve coral reefs, design zero-waste circular loops, and develop solar and geothermal infrastructure.",
        difficulty = "Beginner-friendly",
        estimatedTime = "2 hrs",
        skills = listOf("Ecological Monitoring", "Carbon Accounting", "Circular Economy Design", "Biodiversity Conservation"),
        iconName = "Park",
        colorHex = "#10B981",
        universityMajors = listOf("Environmental Science", "Ecology", "Renewable Energy Engineering"),
        highSchoolSubjects = listOf("Earth Science / Geography", "Biology", "Chemistry"),
        modules = listOf(
            CourseModule(
                id = "env_m1",
                courseId = "environmental_sci",
                title = "Carbon Cycles & The Greenhouse Blanket",
                orderIndex = 1,
                learningObjective = "Examine how greenhouse gases trap infrared radiation and how carbon sinks restore equilibrium.",
                explanation = "The greenhouse effect makes Earth livable! Without it, temperatures would be below freezing. However, burning fossil fuels thickens this thermal blanket, trapping excess heat in oceans and the atmosphere.",
                visualActivity = VisualActivity(
                    title = "The Carbon Sink Balancer",
                    description = "Calculate how restoring mangrove forests absorbs 4x more carbon per acre than terrestrial rainforests.",
                    interactiveType = "EXPLORER",
                    stepsOrScenarios = listOf(
                        "Source: Power plants and transport release CO2 into the atmosphere.",
                        "Sink: Oceans and peat bogs sequester gigatons of dissolved carbon.",
                        "Intervention: Regenerative agriculture and reforestation to reach Net Zero."
                    ),
                    takeaway = "Protecting natural carbon sinks is just as critical as adopting solar and wind energy."
                ),
                realWorldExample = "Costa Rica generating over 98% of its national electricity from clean geothermal, hydro, and wind sources.",
                careerConnection = "Conservation Biologists, Climate Modelers, Sustainability Directors, and Hydrologists.",
                skillDeveloped = "Systems Ecology Thinking",
                estimatedMinutes = 14,
                quiz = MiniQuizQuestion(
                    id = "env_q1",
                    question = "What is a 'carbon sink' in environmental science?",
                    options = listOf(
                        "A kitchen sink made of charcoal",
                        "A natural reservoir (like a forest or ocean) that absorbs and stores carbon from the atmosphere",
                        "A deep gold mine",
                        "A type of solar battery"
                    ),
                    correctIndex = 1,
                    explanation = "Carbon sinks like oceans, peat bogs, and old-growth forests sequester carbon, mitigating greenhouse warming."
                )
            )
        )
    )

    // 15. Biotechnology & Life Sciences
    private fun createCourse15() = Course(
        id = "biotechnology",
        title = "Biotechnology & Life Sciences",
        domain = "Science",
        category = "Genetics & Bioengineering",
        shortDescription = "Explore gene editing with CRISPR, synthetic biology, biofuels, and life-saving vaccines.",
        fullDescription = "Biotechnology rewrites the boundaries of medicine and food production. Using genetic code (DNA/RNA) like software, scientists engineer drought-resistant crops, program bacteria to clean plastic pollution, and eradicate hereditary illnesses.",
        difficulty = "Intermediate",
        estimatedTime = "2.5 hrs",
        skills = listOf("Gene Editing Concepts", "Molecular Biology", "Bioethics", "Laboratory Analysis"),
        iconName = "Biotech",
        colorHex = "#14B8A6",
        universityMajors = listOf("Biotechnology", "Molecular Genetics", "Bioengineering"),
        highSchoolSubjects = listOf("Biology (Genetics)", "Chemistry (Organic)", "Mathematics"),
        modules = listOf(
            CourseModule(
                id = "bio_m1",
                courseId = "biotechnology",
                title = "CRISPR-Cas9: Molecular Scissors",
                orderIndex = 1,
                learningObjective = "Discover how bacteria defend against viruses and how scientists adapted that mechanism for gene editing.",
                explanation = "Bacteria evolved a defense mechanism against viruses: guide RNA finds matching viral DNA, and the Cas9 enzyme cuts it like microscopic scissors! Scientists now use CRISPR to fix mutations that cause genetic diseases like sickle cell anemia.",
                visualActivity = VisualActivity(
                    title = "Target, Snip & Repair",
                    description = "Match a guide RNA sequence (A-U, C-G) to locate and correct a single DNA letter mutation.",
                    interactiveType = "LOGIC_PUZZLE",
                    stepsOrScenarios = listOf(
                        "Step 1: Guide RNA scans the genome for matching 20-base sequence.",
                        "Step 2: Cas9 protein docks at the PAM site and cleaves both strands.",
                        "Step 3: Cellular repair enzymes paste the healthy template DNA sequence."
                    ),
                    takeaway = "Precision gene editing can heal hereditary genetic conditions right at the DNA source."
                ),
                realWorldExample = "In 2023, the FDA approved the first CRISPR-based treatment (Casgevy) curing sickle cell disease.",
                careerConnection = "Geneticists, Bioinformaticians, Clinical Researchers, and Bioprocess Engineers.",
                skillDeveloped = "Molecular Logic & Bioethics",
                estimatedMinutes = 15,
                quiz = MiniQuizQuestion(
                    id = "bio_q1",
                    question = "What role does Cas9 play in the CRISPR system?",
                    options = listOf(
                        "It acts as molecular scissors that cleave DNA at the targeted location",
                        "It heats the test tube to 100 degrees",
                        "It colors the cells blue under a microscope",
                        "It stores computer files in the cloud"
                    ),
                    correctIndex = 0,
                    explanation = "Cas9 is an endonuclease enzyme that precisely cuts double-stranded DNA where the guide RNA directs it."
                )
            )
        )
    )
}
