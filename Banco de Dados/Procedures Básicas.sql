-- TRIGGERS DE DELEÇÃO, PARA TALVEZ DELETAR DO HISTÓRICO E MANTER A COERÊNCIAS

create trigger trDeleteSabor on dbo.Sabor
instead of delete as
BEGIN
	declare @Deletado int
	select @Deletado = id from deleted

	declare @PizzaPedida int
	select @PizzaPedida = dbo.Pizza.id
		from dbo.Pizza
		where primeiroSabor = @Deletado
		    or segundoSabor = @Deletado
	IF @PizzaPedida is not null
	BEGIN
		update dbo.Sabor
		set oficial = -1
		where id = @Deletado
	END
	ELSE
	BEGIN
		delete from dbo.Sabor
		where id = @Deletado
	END
END

-- TRIGGERS DE INSERÇÃO, FAZENDO CÓPIA NA TABELA DE HISTÓRICO

create trigger trInsertCliente on dbo.Cliente
after insert as
BEGIN
	declare @id		  int
	declare @telefone varchar(20)
	declare @nome	  varchar(60)
	declare @endereco varchar(100)
	select @id = id, @telefone = telefone, @nome = nome, @endereco = endereco from inserted
	insert into dbo.HistCliente values (@id, @telefone, @nome, @endereco) 
END

create trigger trInsertClienteSabor on dbo.ClienteSabor
after insert as
BEGIN
	declare @id		   int
	declare @idCliente int
	declare @idSabor   int
	select @id = id, @idCliente = idCliente, @idSabor = idSabor from inserted
	insert into HistClienteSabor values (@id, @idCliente, @idSabor) 
END

create trigger trInsertIngrediente on dbo.Ingrediente
after insert as
BEGIN
	declare @id	  int
	declare @nome varchar(60)
	select @id = id, @nome = nome from inserted
	insert into HistIngrediente values (@id, @nome) 
END

create trigger trInsertPedido on dbo.Pedido 
after insert as
BEGIN
	declare @id		      int
	declare @idCliente    int
	declare @data	      date
	declare @detalhamento varchar(200)
	select @id = id, @idCliente = idCliente, @data = data, @detalhamento = detalhamento from inserted
	insert into HistPedido values (@id, @idCliente, @data, @detalhamento) 
END

create trigger trInsertPedidoPizza on dbo.PedidoPizza
after insert as
BEGIN
	declare @id		  int
	declare @idPedido int
	declare @idPizza  int
	select @id = id, @idPedido = idPedido, @idPizza = idPizza from inserted
	insert into HistPedidoPizza values (@id, @idPedido, @idPizza) 
END

create trigger trInsertPizza on dbo.PedidoPizza
after insert as
BEGIN
	declare @id	           int
	declare @primeiroSabor int
	declare @segundoSabor  int
	select @id = id, @primeiroSabor = primeiroSabor, @segundoSabor = segundoSabor from inserted
	insert into HistPedidoPizza values (@id, @primeiroSabor, @segundoSabor) 
END

create trigger trInsertSabor on dbo.Sabor
after insert as
BEGIN
	declare @id	     int
	declare @nome    int
	declare @oficial int
	declare @preco   decimal(15,2)
	select @id = id, @nome = nome, @oficial = oficial, @preco = preco from inserted
	insert into HistSabor values (@id, @nome, @oficial, @preco) 
END

create trigger trInsertSaborIngrediente on dbo.SaborIngrediente
after insert as
BEGIN
	declare @id		      int
	declare @idIngredient int
	declare @idSabor      int
	select @id = id, @idIngredient = idIngrediente, @idSabor = idSabor from inserted
	insert into HistSaborIngrediente values (@id, @idSabor, @idIngrediente) 
END

-- BLOQUEANDO ATIVIDADE DIRETA NAS TABELAS DE HISTÓRICO
