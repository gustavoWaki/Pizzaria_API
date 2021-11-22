using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using API_Pizzaria.Data;
using API_Pizzaria.Models;
namespace API_Pizzaria.Controllers
{
[Route("api/[controller]")]
[ApiController]
public class ClienteController : Controller
{
private readonly PizzariaContext _context;
public ClienteController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<Cliente>> GetAll() {
return _context.Cliente.ToList();
}

[HttpGet("{ClienteId}")]
        public ActionResult<List<Cliente>> Get(int ClienteId)
        {
            try
            {
                var result = _context.Cliente.Find(ClienteId);
                if (result == null)
                {
                    return NotFound();
                }
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpDelete("{ClienteId}")]
        public async Task<ActionResult> delete(int ClienteId)
        {            
            try
            {
                //verifica se existe Cliente a ser excluído
                var Cliente = await _context.Cliente.FindAsync(ClienteId);
                if (Cliente == null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(Cliente);
                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            // retorna BadRequest se não conseguiu deletar            
        }

        [HttpPost]
        public ActionResult<List<Cliente>> Get(Cliente model)
        {
            try
            {
                var result = _context.Cliente.SingleOrDefault(cliente => cliente.nome == model.nome);
                if(result == null)
                    return BadRequest();
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }

        [HttpPost]
        [Route("criar")]
        public async Task<ActionResult> post(Cliente model)
        {

            try
            {
                var result = _context.Cliente.SingleOrDefault(cliente => cliente.nome == model.nome);
                if(result == null){
                _context.Cliente.Add(model);
                if (await _context.SaveChangesAsync() == 1)
                {
                    //return Ok();
                    return Created($"/api/cliente/{model.id}", model);
                }
                }
                else
                return BadRequest();
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }

            // retorna BadRequest se não conseguiu incluir
            return BadRequest();
        }
}
}