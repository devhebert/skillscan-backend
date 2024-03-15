package com.skillscan.exception.quizattempt;

public class ErrorMessages {
    public static final String INVALID_INPUT = "Verifique se os dados inseridos estão corretos";
    public static final String INVALID_QUESTION_COUNT = "A lista de respostas deve conter exatamente 10 questões";
    public static final String INVALID_QUESTION_OR_ANSWER = "Cada resposta deve conter uma questão e uma resposta válidas";
    public static final String INTERNAL_ERROR = "Ocorreu um erro interno. Por favor, tente novamente mais tarde.";
    public static final String EMPTY_ANSWER = "A resposta não pode estar vazia";
    public static final String EMPTY_QUESTION_ID = "O ID da questão não pode estar vazio";
    public static final String QUIZ_ATTEMPT_TOO_SOON = "Você já fez o teste recentemente. Por favor, tente novamente em %d dias.";
    public static final String QUIZ_ATTEMPT_NOT_FOUND = "Teste não encontrado";
}
