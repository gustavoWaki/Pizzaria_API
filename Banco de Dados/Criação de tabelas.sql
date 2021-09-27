IF (OBJECT_ID('dbo.Cliente') IS NOT NULL) DROP TABLE dbo.Cliente
create table Cliente(
	id int identity not null,
	primary key(id),
	telefone varchar(20) not null,
	nome varchar(60) not null,
	endereco varchar(100)
);

IF (OBJECT_ID('dbo.Ingrediente') IS NOT NULL) DROP TABLE dbo.Ingrediente
create table Ingrediente(
	id int identity not null,
	primary key(id),
	nome varchar(60)
);

IF (OBJECT_ID('dbo.Sabor') IS NOT NULL) DROP TABLE dbo.Sabor
create table Sabor(
	id int identity not null,
	primary key(id),
	nome varchar(60),
	oficial int not null,
	preco decimal(15,2)
);

IF (OBJECT_ID('dbo.ClienteSabor') IS NOT NULL) DROP TABLE dbo.ClienteSabor
create table ClienteSabor(
	id int identity not null,
	primary key(id),
	idCliente int not null,
	foreign key(idCliente) references Cliente(id),
	idSabor int not null,
	foreign key(idSabor) references Sabor(id)
);

IF (OBJECT_ID('dbo.SaborIngrediente') IS NOT NULL) DROP TABLE dbo.SaborIngrediente
create table SaborIngrediente(
	id int identity not null,
	primary key(id),
	idSabor int not null,
	foreign key(idSabor) references Sabor(id),
	idIngrediente int not null,
	foreign key(idIngrediente) references Ingrediente(id)
);

IF (OBJECT_ID('dbo.Pedido') IS NOT NULL) DROP TABLE dbo.Pedido
create table Pedido(
	id int identity not null,
	primary key(id),
	idCliente int not null,
	foreign key(idCliente) references Cliente(id),
	data date not null,
	detalhamento varchar(200) null
);

IF (OBJECT_ID('dbo.Pizza') IS NOT NULL) DROP TABLE dbo.Pizza
create table Pizza(
	id int identity not null,
	primary key(id),
	primeiroSabor int not null,
	foreign key(primeiroSabor) references Sabor(id),
	segundoSabor int not null,
	foreign key(segundoSabor) references Sabor(id)
);

IF (OBJECT_ID('dbo.PedidoPizza') IS NOT NULL) DROP TABLE dbo.PedidoPizza
create table PedidoPizza(
	id int identity not null,
	primary key(id),
	idPedido int not null,
	foreign key(idPedido) references Pedido(id),
	idPizza int not null,
	foreign key(idPizza) references Pizza(id)
);

--HISTÓRICOS

IF (OBJECT_ID('dbo.HistCliente') IS NOT NULL) DROP TABLE dbo.HistCliente
create table HistCliente(
	id int identity not null,
	primary key(id),
	telefone varchar(20) not null,
	nome varchar(60) not null,
	endereco varchar(100)
);

IF (OBJECT_ID('dbo.HistIngrediente') IS NOT NULL) DROP TABLE dbo.HistIngrediente
create table HistIngrediente(
	id int identity not null,
	primary key(id),
	nome varchar(60)
);

IF (OBJECT_ID('dbo.HistSabor') IS NOT NULL) DROP TABLE dbo.HistSabor
create table HistSabor(
	id int identity not null,
	primary key(id),
	nome varchar(60),
	oficial int not null,
	preco decimal(15,2)
);

IF (OBJECT_ID('dbo.HistClienteSabor') IS NOT NULL) DROP TABLE dbo.HistClienteSabor
create table HistClienteSabor(
	id int identity not null,
	primary key(id),
	idCliente int not null,
	foreign key(idCliente) references HistCliente(id),
	idSabor int not null,
	foreign key(idSabor) references HistSabor(id)
);

IF (OBJECT_ID('dbo.HistSaborIngrediente') IS NOT NULL) DROP TABLE dbo.HistSaborIngrediente
create table HistSaborIngrediente(
	id int identity not null,
	primary key(id),
	idSabor int not null,
	foreign key(idSabor) references HistSabor(id),
	idIngrediente int not null,
	foreign key(idIngrediente) references HistIngrediente(id)
);

IF (OBJECT_ID('dbo.HistPedido') IS NOT NULL) DROP TABLE dbo.HistPedido
create table HistPedido(
	id int identity not null,
	primary key(id),
	idCliente int not null,
	foreign key(idCliente) references HistCliente(id),
	data date not null,
	detalhamento varchar(200) null
);

IF (OBJECT_ID('dbo.HistPizza') IS NOT NULL) DROP TABLE dbo.HistPizza
create table HistPizza(
	id int identity not null,
	primary key(id),
	primeiroSabor int not null,
	foreign key(primeiroSabor) references HistSabor(id),
	segundoSabor int not null,
	foreign key(segundoSabor) references HistSabor(id)
);

IF (OBJECT_ID('dbo.HistPedidoPizza') IS NOT NULL) DROP TABLE dbo.HistPedidoPizza
create table HistPedidoPizza(
	id int identity not null,
	primary key(id),
	idPedido int not null,
	foreign key(idPedido) references HistPedido(id),
	idPizza int not null,
	foreign key(idPizza) references HistPizza(id)
);
