package com.creolophus.liuyi.api.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author magicnana
 * @date 2021/7/15 12:22
 */
public class GoogleHashTest {

  @Test
  public void murmur3_32_string(){
    GoogleHash.murmur3_32_string("/sdf/sd/f/dsf/sdf/sd/fsd/f/sdf/sd/fs/df/sdf/sd/fs/df/sd/fs/df/sdf/sd/f/sdf/sd/fs/df/sdf/sd/fs/df/sdf/sd/fs/df/f/dgh/dfghf/hj/gj/gj/gk/h/l/jl/jl/et/er/gd/fg/dfg/d/hghjg/kh/jk/hlk/jhkl/j/kl/jkl/kjl/jklg/yu/r/sdf/gj/juy/egrw/fsdas/df/sdfsdf/sdf/sf/sdf/sdfsdfsdfsfsdfsdf/ghjhlhkd/fghglhjghj");
  }

  @Test
  public void murmur3_32_int(){
    GoogleHash.murmur3_32_string("/sdf/sd/f/dsf/sdf/sd/fsd/f/sdf/sd/fs/df/sdf/sd/fs/df/sd/fs/df/sdf/sd/f/sdf/sd/fs/df/sdf/sd/fs/df/sdf/sd/fs/df/f/dgh/dfghf/hj/gj/gj/gk/h/l/jl/jl/et/er/gd/fg/dfg/d/hghjg/kh/jk/hlk/jhkl/j/kl/jkl/kjl/jklg/yu/r/sdf/gj/juy/egrw/fsdas/df/sdfsdf/sdf/sf/sdf/sdfsdfsdfsfsdfsdf/ghjhlhkd/fghglhjghj");
  }

}