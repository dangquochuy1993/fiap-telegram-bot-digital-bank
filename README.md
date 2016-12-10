#Fiap Telegram Bot Digital Bank

O objetivo desta atividade é criar um bot Telegram que simule um banco virtual, e seja
possível implementar os seguintes comportamentos: (abaixo é informado o objetivo junto ao comando que soluciona o desafio)

 - [x] 01 - Tela de boas-vindas do banco (**/start**)
 - [x] 02 - Criação de conta (**/criarconta**)
 - [x] 03 - Modificação de conta (**/transferirconta**)
 - [x] 04 - Inclusão de dependentes (conta-conjunta) (**/adicionardependente** - **/removerdependente**)
 - [x] 05 - Exibição dos dados do titular e dependentes (**/dadosconta**)    
 - [X] 06 - Depósito (**/depositar**)
 - [X] 07 - Saque (custo do serviço R$ 2,50) (**/saque**)                    
 - [X] 08 - Solicitação de extrato (custo do serviço R$ 1,00) (**/extrato**)    
 - [X] 09 - Solicitação de empréstimo, cujo prazo máximo é de 3 anos e valor máximo de 40 vezes o saldo da conta (custo do serviço R$ 15,00 além de juros de 5% a.m.) (**/emprestimo**)                
 - [X] 10 - Exibição de saldo devedor do empréstimo e prazo de pagamento (**/extratoemprestimo**)    
 - [X] 11 - Exibição dos lançamentos detalhada, com somatória ao final (**/extratodetalhado**)    
 - [X] 12 - Exibição das retiradas, com somatória ao final (**/extratosaques**)            
 - [X] 13 - Exibição das tarifas de serviço, com somatória ao final dos serviços que foram utilizados na conta (**/extratotarifas**)
 - [X] 14. Tela de ajuda (**/ajuda**)    
 - [X] Todas as operações devem ser armazenadas para futuras consultas em arquivo texto. **(Todo processo está sendo serializado e gravado no caminho de session.path configurado no arquivo config.properties. Dessa forma o programa pode ser parado e iniciado e as informações não serão perdidas)**   
    
##Configuração config.properties

Acesse o diretório resources e copie o conteúdo do arquivo **config.properties.dist**. Cole o conteúdo em um arquivo chamado **config.properties** e configure o token do telegram.

##Executando o projeto

No diretório **src** existe um arquivo chamado **Main.java** no pacote **br.com.fiap.telegram**. Abra esse arquivo e execute **Run As**.


##Guia de uso

[Acesse o guia de uso](GUIA_DE_USO.md)

##Diagramas de Classe

 - [Modelo](docs/diagramas/classe-modelo.png)
 - [Action](docs/diagramas/classe-action.png)
 - [Command](docs/diagramas/classe-command.png)
 - [Printer](docs/diagramas/classe-printer.png)
 - [Handler](docs/diagramas/classe-handler.png)
 
##Diagramas de Sequência
 
 - [Fluxo programa](docs/diagramas/sequencia-programa.png)
 - [Fluxo telegram](docs/diagramas/sequencia-telegram.png)
 
##Java DOC
 