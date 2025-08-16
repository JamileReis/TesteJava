import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        Scanner sc = new Scanner(System.in);
        List<Funcionario> funcionarios = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1 - Inserir funcionário");
            System.out.println("2 - Remover funcionário");
            System.out.println("3 - Listar funcionários");
            System.out.println("4 - Sair");
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
                    System.out.println("Funcionário adicionado!");
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
                    System.out.println("Saindo...");
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void imprimirFuncionarios(List<Funcionario> lista, DateTimeFormatter formatter, NumberFormat nf) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
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

