# sawhet-code

Info:

Sawhet is a text extraction and storage system for lab reports written by biology students. 
The code is not a standalone application but relies on output from Qualtrics, processing with GATE (General Architecture for Text Engneering) and a mysql database.

Workflow:

Students enter lab reports through a Qualtrics survey. Sawhet downloads the text and sends it to GATE for processing. GATE annotates each lab report and extracts information about the student (name, email, associated teaching assistant) and text properties such as citations and biology terms. Sawhet stores this information in a database and analyses the results in comparison to other lab reports already in the database. Sawhet also quicly checks if lab reports are potentially plagiarised and outputs corresponding lab report IDs from the database accordingly. 

Purpose:

The reason for building this software is threefold:
1) Create opportunity for students to improve their lab reports based on Sawhet's pre-analysis, before grading occurs.
2) Assist teaching assistants in the grading process.
3) Curate student lab reports in an effective manner.
