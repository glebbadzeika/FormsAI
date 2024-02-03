# FormsAI

## Overview

FormsAI is a web-based application that serves as an innovative alternative to Google Forms, incorporating AI capabilities to dynamically alter the options of closed questions based on responses to other fields. This feature enriches the form-filling experience, enhances response analysis, and streamlines user interaction with the form.

## Features

- **Dynamic Questionnaire Adjustment**: Automatically modifies options for closed questions based on previous answers, facilitating a more tailored and efficient form-filling process.
- **AI Integration**: Leverages OpenAI's API to analyze responses in real-time and suggest adjustments to the questionnaire, ensuring continuous improvement and relevance.
- **Advanced Analysis Tools**: You can generate plots, based on the forms' answers.

## Getting Started

### Prerequisites

- Java JDK 11 or newer
- Maven
- Spring Boot
- An OpenAI API key

### Setup

1. **Clone the Repository**

```bash
git clone [repository-url]
cd FormsAI
```

2. **Build the Project**

```bash
mvn clean install
```

3. **Run the Application**

```bash
mvn spring-boot:run
```

## Usage

- **Form Creation:** Access the form creation REST API interface to design your form. Add questions and define their type.
- **Response Analysis:** Generate comprehencive PDFs with analytics, based on the improved form.


