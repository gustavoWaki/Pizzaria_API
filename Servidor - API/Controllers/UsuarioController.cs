using System.Collections.Generic;
using System.Linq;
using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using API_Pizzaria.Data;
using API_Pizzaria.Models;
namespace API_Pizzaria.Controllers
{
[Route("api/[controller]")]
[ApiController]
public class UsuarioController : Controller
{
private readonly PizzariaContext _context;
public UsuarioController(PizzariaContext context)
{
// construtor
_context = context;
}
[HttpGet]
public ActionResult<List<Usuario>> GetAll() {
return _context.Usuario.ToList();
}

[HttpGet("{UsuarioId}")]
        public ActionResult<List<Usuario>> Get(int UsuarioId)
        {
            try
            {
                var result = _context.Usuario.Find(UsuarioId);
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

        [HttpDelete("{UsuarioId}")]
        public async Task<ActionResult> delete(int UsuarioId)
        {            
            try
            {
                //verifica se existe Usuario a ser excluído
                var Usuario = await _context.Usuario.FindAsync(UsuarioId);
                if (Usuario == null)
                {
                    //método do EF
                    return NotFound();
                }
                
                _context.Remove(Usuario);
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
        public async Task<IActionResult> post(Usuario model)
        {
            try
            {
                //verifica se existe Usuario a ser alterado
                var result = _context.Usuario.SingleOrDefault(user => user.nome == model.nome);
                if(result == null)
                    return BadRequest();
                if (result.senha != model.senha)
                {
                    return BadRequest();
                }
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }
}
}