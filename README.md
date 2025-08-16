# Sistema de Gerenciamento de Funcionários

## Sobre o Projeto
Este projeto implementa uma aplicação simples em **Java** para gerenciar informações de funcionários. Ele permite inserir, remover e listar funcionários utilizando entrada de dados pelo console. Além disso, realiza a formatação de datas e valores monetários para exibição amigável.

O sistema é útil como exemplo de manipulação de **listas**, uso de **Scanner** para entrada de dados e **formatadores** para datas e moeda em Java.

## Funcionalidades
- Inserir funcionários com informações como nome, data de nascimento, salário e função.
- Remover um funcionário específico da lista.
- Listar todos os funcionários com suas informações formatadas:
  - Data de nascimento no formato `dd/MM/yyyy`.
  - Salário formatado como moeda brasileira (R$).

## Tecnologias Utilizadas
- Java 21
- `java.util.List` para armazenamento de dados
- `java.util.Scanner` para entrada de dados
- `java.time.LocalDate` e `java.time.format.DateTimeFormatter` para manipulação de datas
- `java.text.NumberFormat` para formatação de valores monetários
