#Guia de Uso

Abaixo serão apresentados um guia para ajudá-lo com a utilização do BOT.

##O BOT

Ao acessar o BOT pela primeira vez será apresentado uma tela com informações sobre o BOT.

![Start](docs/prints/1-start-sm.png)

##/start

Clique em começar para iniciar a execução do BOT. O comando **/start** será executado. Uma tela de boas vindas será apresentada.


![Start](docs/prints/2-start-sm.png)


Nessa tela podemos executar duas ações, **/criarconta** ou usar a opção **/ajuda**.

##/ajuda

Um menu com todas opções disponíveis será exibido.

![Ajuda](docs/prints/9-ajuda-sm.png)

##/criarconta

Em criar conta começamos a utilização do BOT. Essa opção criará uma nova conta no banco virtual. Esse comando funciona em dois passos, no primeiro passo será solicitado a digitação do nome completo.

![Criar Conta](docs/prints/3-criarconta-sm.png)

No segundo passo será solicitado o valor inicial de abertura da conta.

![Criar Conta](docs/prints/4-criarconta-sm.png)

Após a digitação dessas informações uma tela de sucesso será exibida. Essa tela informa que a conta foi criada com sucesso, e exibe dados básicos da conta, tais como o nome do cliente, número da conta e o saldo.

#O que fazer após a criação da conta

Logo após a criação da conta o cliente está livre para realizar qualquer ação no banco virtual. Abaixo damos dicas para os próximos passos.

##/adicionardependente

Nessa opção será informado o nome do dependente, basta informá-lo para vincular outro usuário a sua conta.

![Adicionar Dependente](docs/prints/5-adicionardependente-sm.png)

##/removerdependente

Se desejar remover um dependente basta informar o nome desse dependente.

![Remover Dependente](docs/prints/6-removerdependente-sm.png)

Se preferir clique no botão que será apresentado trazendo o nome do(s) dependente(s).

![Remover Dependente Botao](docs/prints/6-removerdependente-botao-sm.png)

##/saque

Ao solicitar a opção saque alguns botões com valores personalizados serão exibidos.

![Saque Botao](docs/prints/7-saque-botao-sm.png)

Se preferir, informe o valor desejado no campo texto. O valor deve ser inteiro ou um double.

![Saque](docs/prints/7-saque-sm.png)

Após realização do saque uma mensagem de sucesso será exibida com o saldo atual de sua conta.

##/depositar

A opção depositar é muito parecida com a opção saque. Alguns botões com valores personalizados serão exibidos.

![Depositar Botao](docs/prints/7-saque-botao-sm.png)

Se preferir você pode informar o valor a ser depositado no campo texto.

![Depositar](docs/prints/8-depositar-sm.png)

Ao finalizar a operação uma tela de sucesso será exibida com o saldo atual de sua conta.

##/dadosconta

Essa opção irá retornar seus dados pessoais, dados resumidos de sua conta, saldo e dependentes. 

![Dados Conta](docs/prints/10-dadosconta-sm.png)

##Extratos - /extratodetalhado

O sistema possui diversos extratos:

 - /extrato
 - /extratosaques
 - /extratotarifas
 - /extratodetalhado
 - /extratoemprestimo

Obs: Na opção **/ajuda** você tem mais detalhes sobre cada extrato.
 
O funcionamento das opções extratos são parecidas, apenas o contexto da informação é diferente. Por exemplo, no extrato de saques, apenas informações sobre saque é apresentada. A fim de simplificar o uso desse guia vamos apresentar o extrato detalhado.

Abaixo um exemplo da saída do comando **/extratodetalhado**.

![Extrato](docs/prints/11-extratos-sm.png)

No final do extratodetalhado é apresentado uma somatória das movimentações.

![Extrato Somatoria](docs/prints/11-extratos-somatoria-sm.png)


##/transferirconta (Simula um processo de trag)

Após a digitação desse comando será perguntado se deseja continuar ou não com o processo de transferência.

![Transferencia de Conta](docs/prints/12-trasferencia-conta-trag-sm.png)


![Transferencia de Conta Botao](docs/prints/12-trasferencia-conta-trag-botao-sm.png)

Se for informado que sim a transferência será executada.

![Transferencia de Conta](docs/prints/13-trasferencia-conta-trag-sm.png)

No último passo do processo de transferência é exibido o número da conta nova e informações da conta antiga. Observe que a conta antiga está zerada.

##/emprestimo

Ao solicitar a opção emprestimo será solicitado o tempo de pagamento.

![Empresitimo Botao](docs/prints/14-emprestimo-botao-sm.png)

Caso os prazos personalizados não atendam a sua necessidade o sistema possui uma inteligência de linguagem natural com algumas opções:

![Emprestimo](docs/prints/14-emprestimo-sm.png)

A expressão aceita um **número** seguida da palavra **ano, anos, meses ou mês**. No exemplo selecionamos 24 meses.

Em seguida será solicitado o valor do empréstimo. 

![Emprestimo](docs/prints/15-emprestimo-sm.png)

Ao final da operação de empréstimo o sistema apresentará informações resumidas do empréstimo que acabou de realizar, data final do pagamento, juros ao mês e o novo saldo de sua conta.

![Emprestimo](docs/prints/16-emprestimo-sm.png)

##Observações

Apenas o comando **/criarconta** deve ser executado primeiro. Após criação da conta você está livre para digitar os comandos em qualquer ordem.