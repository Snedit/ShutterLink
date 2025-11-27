import os
import google.generativeai as genai
from git import Repo

genai.configure(api_key=os.getenv("GEMINI_API_KEY"))

repo = Repo(".")

HEAD = repo.commit("HEAD")
PARENT = HEAD.parents[0]

diff_text = repo.git.diff(PARENT.hexsha, HEAD.hexsha)

if not diff_text.strip():
    print("No changes detected — skipping.")
    exit(0)

prompt = f"""
You are a senior software architect and documentation expert.
Generate updated technical documentation based on the code diff.

--- DIFF START ---
{diff_text}
--- DIFF END ---

Write documentation that includes:
- Summary of changes
- Why the change was likely made
- Updated API documentation (Spring Boot)
- Updated architectural explanation
- Mermaid diagrams if relevant
- Clean Markdown format

Do NOT write code unless needed for examples.
"""

response = genai.GenerativeModel("gemini-2.5-flash").generate_content(prompt)

documentation = response.text

os.makedirs("docs", exist_ok=True)
print("The file should have this: \n ", documentation)
with open("docs/CHANGELOG_AI.md", "w", encoding="utf-8") as f:
    f.write(documentation)

print("Gemini documentation generated.")
