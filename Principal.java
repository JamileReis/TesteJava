import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        Scanner sc = new Scanner(System.in);
        List<Funcionario> funcionarios = new ArrayList<>();

        inserirFuncionariosPadrao(funcionarios);
        System.out.println("Funcionários padrão inseridos automaticamente!");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1 - Inserir funcionário manualmente");
            System.out.println("2 - Remover funcionário");
            System.out.println("3 - Listar funcionários");
            System.out.println("4 - Aumentar salário em 10% de um funcionário específico");
            System.out.println("5 - Agrupar funcionários por função");
            System.out.println("6 - Funcionários que fazem aniversário nos meses 10 e 12");
            System.out.println("7 - Funcionário mais velho");
            System.out.println("8 - Total dos salários");
            System.out.println("9 - Salários em salários mínimos");
            System.out.println("10 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Digite a data de nascimento (dd/MM/yyyy): ");
                    LocalDate nascimento = LocalDate.parse(sc.nextLine(), formatter);
                    System.out.print("Digite o salário (ex: 1.212,00): ");
                    String salarioStr = sc.nextLine().replace(".", "").replace(",", ".");
                    BigDecimal salario = new BigDecimal(salarioStr);
                    System.out.print("Digite a função: ");
                    String funcao = sc.nextLine();
                    funcionarios.add(new Funcionario(nome, nascimento.atStartOfDay(), salario, funcao));
                    System.out.println("Funcionário adicionado manualmente!");
                    break;

                case 2:
                    System.out.print("Digite o nome do funcionário a remover: ");
                    String nomeRemover = sc.nextLine();
                    boolean removed = funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nomeRemover));
                    if (removed) System.out.println("Funcionário removido!");
                    else System.out.println("Funcionário não encontrado!");
                    break;

                case 3:
                    imprimirFuncionarios(funcionarios, formatter, numberFormat);
                    break;

                case 4:
                    aumentarSalarioEspecifico(funcionarios, sc, numberFormat);
                    break;

                case 5:
                    agruparPorFuncao(funcionarios, formatter, numberFormat);
                    break;

                case 6:
                    aniversariantesOutubroDezembro(funcionarios, formatter, numberFormat);
                    break;

                case 7:
                    funcionarioMaisVelho(funcionarios);
                    break;

                case 8:
                    totalSalarios(funcionarios, numberFormat);
                    break;

                case 9:
                    salariosMinimos(funcionarios, numberFormat);
                    break;

                case 10:
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void inserirFuncionariosPadrao(List<Funcionario> funcionarios) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter).atStartOfDay(),
                new BigDecimal("2009.44"), "Operador"));

        funcionarios.add(new Funcionario("João", LocalDate.parse("12/05/1990", formatter).atStartOfDay(),
                new BigDecimal("2284.38"), "Operador"));

        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter).atStartOfDay(),
                new BigDecimal("9836.14"), "Coordenador"));

        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter).atStartOfDay(),
                new BigDecimal("19119.88"), "Diretor"));

        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter).atStartOfDay(),
                new BigDecimal("2234.68"), "Recepcionista"));

        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter).atStartOfDay(),
                new BigDecimal("1582.72"), "Operador"));

        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter).atStartOfDay(),
                new BigDecimal("4071.84"), "Contador"));

        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter).atStartOfDay(),
                new BigDecimal("3017.45"), "Gerente"));

        funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter).atStartOfDay(),
                new BigDecimal("1606.85"), "Eletricista"));

        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter).atStartOfDay(),
                new BigDecimal("2799.93"), "Gerente"));
    }

    private static void aumentarSalarioEspecifico(List<Funcionario> funcionarios, Scanner sc, NumberFormat nf) {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.print("Digite o nome do funcionário que receberá o aumento: ");
        String nomeAumento = sc.nextLine();

        boolean encontrado = false;
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getNome().equalsIgnoreCase(nomeAumento)) {
                BigDecimal salarioAtual = funcionario.getSalario();
                BigDecimal aumento = salarioAtual.multiply(new BigDecimal("0.10"));
                BigDecimal novoSalario = salarioAtual.add(aumento);

                System.out.println("\n--- Aumento de Salário ---");
                System.out.println("Funcionário: " + funcionario.getNome());
                System.out.println("Salário atual: " + nf.format(salarioAtual));
                System.out.println("Aumento (10%): " + nf.format(aumento));
                System.out.println("Novo salário: " + nf.format(novoSalario));

                funcionario.setSalario(novoSalario);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Funcionário não encontrado!");
        }
    }

    private static void agruparPorFuncao(List<Funcionario> funcionarios, DateTimeFormatter formatter, NumberFormat nf) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            funcionariosPorFuncao.putIfAbsent(funcao, new ArrayList<>());
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        System.out.println("\n--- Funcionários Agrupados por Função ---");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("\nFunção: " + entry.getKey());
            System.out.println("-----------------------------------");
            for (Funcionario f : entry.getValue()) {
                System.out.printf("%-15s %-15s %-15s%n",
                        f.getNome(),
                        f.getDataNascimento().format(formatter),
                        nf.format(f.getSalario()));
            }
        }
    }

    private static void aniversariantesOutubroDezembro(List<Funcionario> funcionarios, DateTimeFormatter formatter, NumberFormat nf) {
        System.out.println("\n--- Aniversariantes dos meses 10 e 12 ---");
        System.out.printf("%-15s %-15s %-15s %-15s%n", "Nome", "Nascimento", "Salário", "Função");
        System.out.println("---------------------------------------------------------------");

        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.printf("%-15s %-15s %-15s %-15s%n",
                        f.getNome(),
                        f.getDataNascimento().format(formatter),
                        nf.format(f.getSalario()),
                        f.getFuncao());
            }
        }
    }

    private static void funcionarioMaisVelho(List<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        Funcionario maisVelho = null;
        int maiorIdade = -1;

        for (Funcionario f : funcionarios) {
            int idade = Period.between(f.getDataNascimento().toLocalDate(), LocalDate.now()).getYears();
            if (idade > maiorIdade) {
                maiorIdade = idade;
                maisVelho = f;
            }
        }

        if (maisVelho != null) {
            System.out.println("\n--- Funcionário Mais Velho ---");
            System.out.println("Nome: " + maisVelho.getNome());
            System.out.println("Idade: " + maiorIdade + " anos");
            System.out.println("Data de Nascimento: " + maisVelho.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    private static void totalSalarios(List<Funcionario> funcionarios, NumberFormat nf) {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Funcionario f : funcionarios) {
            total = total.add(f.getSalario());
        }
        System.out.println("\n--- Total dos Salários ---");
        System.out.println("Total: " + nf.format(total));
    }

    private static void salariosMinimos(List<Funcionario> funcionarios, NumberFormat nf) {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        System.out.println("\n--- Salários em Salários Mínimos ---");
        System.out.println("Salário Mínimo: " + nf.format(salarioMinimo));
        System.out.printf("%-15s %-15s %-20s%n", "Nome", "Salário", "Salários Mínimos");
        System.out.println("---------------------------------------------------");

        for (Funcionario f : funcionarios) {
            BigDecimal qtdSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.printf("%-15s %-15s %-20.2f%n",
                    f.getNome(),
                    nf.format(f.getSalario()),
                    qtdSalariosMinimos);
        }
    }

    private static void imprimirFuncionarios(List<Funcionario> lista, DateTimeFormatter formatter, NumberFormat nf) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.println("\n--- Lista de Funcionários ---");
        System.out.printf("%-15s %-15s %-15s %-15s%n", "Nome", "Nascimento", "Salário", "Função");
        System.out.println("---------------------------------------------------------------");
        for (Funcionario f : lista) {
            System.out.printf("%-15s %-15s %-15s %-15s%n",
                    f.getNome(),
                    f.getDataNascimento().format(formatter),
                    nf.format(f.getSalario()),
                    f.getFuncao());
        }
    }
}