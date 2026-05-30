# Guia de Padrão de Commits — Barbearia

## Por que seguir um padrão?

O professor avaliará os nomes dos commits. Commits bem nomeados mostram
organização e profissionalismo, além de facilitar o entendimento do histórico
do projeto.

---

## Formato

```
tipo: descrição curta do que foi feito
```

- Sempre em **letras minúsculas**
- Descrição **curta e específica**
- Escrever em **português**
- Um commit por funcionalidade — não juntar tudo em um commit só

---

## Tipos

| Tipo       | Quando usar                                          |
|------------|------------------------------------------------------|
| `feat`     | Quando adiciona algo novo (classe, método, tela...)  |
| `fix`      | Quando corrige um erro ou bug                        |
| `refactor` | Quando melhora o código sem mudar o funcionamento    |
| `docs`     | Quando mexe em documentação (README, comentários...) |
| `chore`    | Configurações do projeto (pom.xml, .gitignore...)    |

---

## Exemplos por etapa do projeto

### Configuração inicial
```
chore: cria estrutura do projeto maven
chore: adiciona dependência do sqlite no pom.xml
chore: adiciona .gitignore para java
docs: adiciona README com instruções do projeto
```

### Model
```
feat: adiciona classe Barbearia
feat: adiciona classe Barbeiro
feat: adiciona classe Servico
feat: adiciona classe Agendamento
```

### Database
```
feat: adiciona DatabaseConnection com sqlite
feat: adiciona criação das tabelas no banco de dados
fix: corrige tipo de dado da coluna dataHora
```

### Repository
```
feat: adiciona BarbeiroRepository
feat: adiciona ServicoRepository
feat: adiciona AgendamentoRepository
feat: adiciona método listarPorData em AgendamentoRepository
fix: corrige mapeamento de horarioInicio em BarbeiroRepository
```

### Service
```
feat: adiciona BarbeiroService
feat: adiciona ServicoService
feat: adiciona AgendamentoService
feat: adiciona validação de expediente no AgendamentoService
feat: adiciona regra de antecedência mínima no cancelamento
fix: corrige validação de barbeiro ativo no BarbeiroService
refactor: simplifica método autenticar em BarbeiroService
```

### Controller
```
feat: adiciona BarbeiroController
feat: adiciona ServicoController
feat: adiciona AgendamentoController
```

### View
```
feat: adiciona menu principal
feat: adiciona menu de barbeiros
feat: adiciona menu de serviços
feat: adiciona menu de agendamentos
fix: corrige exibição de horários no menu de agendamentos
refactor: melhora formatação do menu principal
```

---

## O que evitar

❌ `feat: atualiza código`
❌ `fix: correção`
❌ `commit`
❌ `alterações`
❌ `versão final`
❌ `agora vai`

✅ `feat: adiciona validação de horário no AgendamentoService`
✅ `fix: corrige verificação de barbeiro ativo ao criar agendamento`

---

## Fluxo de commit no VSCode

Após fazer alterações no código, abrir o terminal e rodar:

```bash
git add .
git commit -m "feat: adiciona classe Barbeiro"
git push origin feature/SEU_NOME
```

---

## Lembrete importante

O professor exige **ao menos 1 commit por semana** por integrante.
Não deixem acumular — commitem sempre que terminar uma classe ou método.
