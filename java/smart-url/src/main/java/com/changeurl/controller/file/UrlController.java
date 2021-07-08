package com.changeurl.controller.file;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/change")
@Api("urlController||url转换控制器")
public class UrlController {


	@PostMapping("/url")
	@ApiOperation("url||URL转换")
	public @ResponseBody
	void changeUrl(@RequestBody String url) {

	}

}